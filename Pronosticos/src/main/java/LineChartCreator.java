import javafx.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public class LineChartCreator {


    XSSFWorkbook wb;

    public LineChartCreator() {
        wb = new XSSFWorkbook();
    }

    //pronosticValues es una lista de 23 valores que son los valores del pronostico
    public void newLineChart(String sheetName, List<Double> ventas, List<String> names, List<Double> resultados, String pronosticName, String nameFirstRows) throws FileNotFoundException, IOException {
        try {

            XSSFSheet sheet = wb.createSheet(sheetName);

            // Months Names
            Row row = sheet.createRow((short) 0);
            writeRowValues(row,nameFirstRows,null,names,true);


            // Ventas
            row = sheet.createRow((short) 1);
            writeRowValues(row,"Ventas",ventas,null,false);


            // Resultado pronostico

            row = sheet.createRow((short) 1);
            writeRowValues(row,pronosticName,resultados,null,false);


            //EMC creation
            row = sheet.createRow((short) 2);
            List<Double> emcs = new ArrayList<>();
            emcs = getEMC(ventas,resultados);
            writeRowValues(row,"EMC",emcs,null,false);

            //EMC average
            if(!sheetName.contains("winters")){
                row = sheet.createRow((short) 3);
                Double averageEmcs = 0D;
                averageEmcs = getEMCAverage(emcs);
                writeValue(row,"EMC Average",averageEmcs);
            }

            //ubicacion del dibujo
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 15, 26);

            //nombre de la linea, color y la ubicacion de la leyenda
            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText(pronosticName);
            chart.setTitleOverlay(false);

            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            //los valores de abajo del dibujo
            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);

            //los valores de la izquierda del dibujo
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

            //reconocimiento de las celdas dondes estan los valores para hacer la operacion de Excel para dibujar la linea correctamente
            XDDFDataSource<String> months = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                    new CellRangeAddress(0, 0, 1, names.size()));

            XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                    new CellRangeAddress(1, 1, 1, names.size()));

            XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

            XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(months, values);
            series1.setTitle("Pronostico", null);
            series1.setSmooth(false);
            series1.setMarkerStyle(MarkerStyle.STAR);


            chart.plot(data);

            // escribe el resultado en una nueva hoja
            String filename = "resultados_pronosticos.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                wb.write(fileOut);
            }
        }
        catch (Exception exception){
            throw new FileNotFoundException(exception.getMessage());
        }
    }

    public Workbook loadExcel(String path) throws IOException {
        Workbook wb = WorkbookFactory.create(new File(path));
        return wb;
    }


    public void createChartWithFile(String path) throws IOException {

        List<Double> ventas = getExcelValues(path);
        List<String> meses = getExcelNames(path);
        List<String> semestres = getSemestres();
        List<Double> periodos = getPeriodos();

        String promedioMovilName = "promedioMovil";
        String promedioMovilDobleName = "promedioMovilDoble";
        String suavizacionExponencialName = "suavizacionExponencial";
        String holtName = "holt";
        String wintersName = "winters";
        String regresionLinealName = "regresion lineal";
        String descompiscionSeriesName = "descomposicion de series";

        //promedio movil
        List<Double> promedioMovilResultados;
        PromedioMovil promedioMovil = new PromedioMovil();
        promedioMovilResultados = promedioMovil.getPromedioMovil(ventas);
        newLineChart(promedioMovilName,ventas, meses, promedioMovilResultados, "Promedio Movil","Meses");

        //promedio movil doble
        List<Double> promedioMovilDobleResultados;
        PromedioMovilDoble promedioMovilDoble = new PromedioMovilDoble();
        promedioMovilDobleResultados = promedioMovilDoble.getPromedioMovilDoble(ventas);
        newLineChart(promedioMovilDobleName,ventas, meses, promedioMovilDobleResultados, "Promedio Movil Doble","Meses");

        //suavizacion exponencial
        List<Double> suavizacionExponencialResultados;
        SuavizacionExponecial suavizacionExponecial = new SuavizacionExponecial();
        Double alphaSuavizacion = 0.4;
        suavizacionExponencialResultados = suavizacionExponecial.getSuavizacionExponencial(ventas,alphaSuavizacion);
        newLineChart(suavizacionExponencialName,ventas,meses,suavizacionExponencialResultados,"Suavizacion Exponencial","Meses");

        //Holt
        List<Double> holtResultados;
        Holt holt = new Holt();
        Double alphaHolt = 0.50;
        Double betaHolt = 0.10;
        holtResultados = holt.getHolt(ventas,alphaHolt,betaHolt);
        newLineChart(holtName,ventas,meses,holtResultados,"Holt","Meses");

        //Winters
//        Pair<List<Double>,List<Double>> wintersResultado;
        List<List<Double>> wintersResultado;
        Winters winters = new Winters();
        Double alphaWinters = 0.90;
        Double betaWinters = 0.90;
        Double gammaWinters = 0.10;
        wintersResultado = winters.getWinters(ventas,alphaWinters,betaWinters,gammaWinters,6);
        newLineChart(wintersName,wintersResultado.get(0),semestres,wintersResultado.get(1),"Winters","Semestres");

        //Regresion Lineal
        List<Double> regresionLinealResultados;
        Regresion regresion = new Regresion(periodos,ventas);
        //m & b para propositos de test
        Double m = regresion.getM();
        Double b = regresion.getB();
        regresionLinealResultados = regresion.getPronostico();
        newLineChart(regresionLinealName,ventas,meses,regresionLinealResultados,"Regresion Lineal","Meses");

        //Descomposicion de series
        List<Double> descomposicionSeriesResultado;
        DescomposicionSeries descomposicionSeries = new DescomposicionSeries(periodos);
        Double promedioVentas = descomposicionSeries.getPromedioVentas(ventas);
        List<Double> promedioTri = descomposicionSeries.getPromedioTrimestres(ventas);
        List<Double> factoresEstacionales = descomposicionSeries.getFactorEstacional(promedioTri,promedioVentas,ventas);
        descomposicionSeries.getDemandaDesestacionalizada(ventas,factoresEstacionales);

        //m & b para propositos de test
        Double mDescoposicion = descomposicionSeries.getM();
        Double bDescoposicion = descomposicionSeries.getB();
        List<Double> estimacionTendencia = descomposicionSeries.getTendencia();
        descomposicionSeriesResultado = descomposicionSeries.getPronostico(estimacionTendencia,factoresEstacionales);
        newLineChart(descompiscionSeriesName,ventas,meses,descomposicionSeriesResultado,"Descomposicion de Series","Meses");


    }

    public List<Double> getExcelValues(String name) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(name));
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowIndex = 2;
        int lastRowIndex = 25;
        int cellIndex = 3;

        List<Double> values = new ArrayList<>();
        for (int currentIndex = firstRowIndex; currentIndex <= lastRowIndex; currentIndex++){
            Row row = sheet.getRow(currentIndex);
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            values.add(cell.getNumericCellValue());
        }

        return values;
    }

    public List<String> getExcelNames(String name) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(name));
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowIndex = 2;
        int lastRowIndex = 25;
        int cellIndex = 2;

        List<String> names = new ArrayList<>();
        for (int currentIndex = firstRowIndex; currentIndex <= lastRowIndex; currentIndex++){
            Row row = sheet.getRow(currentIndex);
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            names.add(cell.getStringCellValue());
        }

        return names;
    }

    public List<String> getSemestres() throws IOException {

        List<String> names = new ArrayList<>();
        names.add("Semestre 1 2020");
        names.add("Semestre 2 2020");
        names.add("Semestre 1 2021");
        names.add("Semestre 2 2021");

        return names;
    }

    public List<Double> getPeriodos(){
        List<Double> resultado = new ArrayList<>();
        for(Double i = 1D ; i < 25D ; i++){
            resultado.add(i);
        }
        return resultado;
    }

    public List<Double> getEMC(List<Double> ventas,List<Double> promedios ){
        List<Double> result = new ArrayList<>();
        int index = 0;
        for (int i = index; i < ventas.size(); i++) {
            if(promedios.get(i) == 0){
                result.add(0D);
            }
            else{
                result.add(Math.pow(ventas.get(i) - promedios.get(i),2));
            }
        }
        return result;
    }

    public Double getEMCAverage(List<Double> emcs){
        Double emcsCount = 0D;
        Double sumEmcs = 0D;
        Double average = 0D;

        for (int i = 13; i < emcs.size(); i++){
            emcsCount++;
            sumEmcs += emcs.get(i);
        }
        average = sumEmcs/emcsCount;
        return average;
    }

    public void writeRowValues(Row row, String rowName, List<Double> list, List<String> names, Boolean firstRow){

        Cell cell = row.createCell((short) 0);
        cell.setCellValue(rowName);
        short i = 1;

        //for the month names
        if(firstRow){
            for (String value : names){
                cell = row.createCell(i);
                cell.setCellValue(value);
                i++;
            }
        }
        //for everything else
        else{
            for (Double value : list){
                cell = row.createCell(i);
                cell.setCellValue(value);
                i++;
            }
        }

    }

    public void writeValue(Row row, String rowName, Double value){

        Cell cell = row.createCell((short) 0);
        cell.setCellValue(rowName);
        short i = 1;

        cell = row.createCell(i);
        cell.setCellValue(value);

    }


}
