import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DescomposicionSeries {

    //periodo = x
    //demandaDesestacionalizada = y

    private List<Double> x;
    private List<Double> y;
    private int n;          //número de datos
    public double a, b, m;    //pendiente y ordenada en el origen
    public DescomposicionSeries(List<Double> x) {
        this.x=x;
        n=x.size(); //número de datos
    }

    public Double getM(){
        Double resultado = 0D;
        resultado += (n * getSumatoriaXY(x,y)) - (getSumatoriaLista(x) * getSumatoriaLista(y));
        resultado /= (n * getSumatoriaListaAlCuadrado(x)) - Math.pow(getSumatoriaLista(x),2);
        a = m = resultado;
        return resultado;
    }

    public Double getB(){
        Double resultado = 0D;
        resultado += getSumatoriaLista(y) - (m * getSumatoriaLista(x));
        resultado /= n;
        b = resultado;
        return resultado;
    }

    public List<Double> getTendencia(){
        List<Double> resultado = new ArrayList<>();
        for (Double x : this.x){
            resultado.add(b + (a * x));
        }
        return resultado;
    }


    public Double getSumatoriaXY(List<Double> x, List<Double> y){
        Double resultado = 0D;
        for (int i = 0; i < x.size(); i++){
            resultado += x.get(i) * y.get(i);
        }
        return resultado;
    }

    public Double getSumatoriaLista(List<Double> lista){
        Double resultado = 0D;
        for (int i = 0; i < lista.size(); i++){
            resultado += lista.get(i);
        }
        return resultado;
    }

    public Double getSumatoriaListaAlCuadrado(List<Double> lista){
        Double resultado = 0D;
        for (int i = 0; i < lista.size(); i++){
            resultado += Math.pow(lista.get(i),2);
        }
        return resultado;
    }

    public Double getPromedioVentas(List<Double> ventas){
        Double resultado = 0D;
        for (Double venta : ventas){
            resultado += venta;
        }
        return (resultado/Double.valueOf(ventas.size()));
    }

    public List<Double> getPromedioTrimestres(List<Double> ventas){
        List<Double> resultado = new ArrayList<>();
        Integer index = 0;

        for (int i = index; i < 6; i++){
            Double sumaVentasTri = 0D;
            Double ventasCount = 0D;
            for (int yIndex = i; yIndex < ventas.size(); yIndex+= 6){
                if(yIndex < ventas.size()){
                    sumaVentasTri += ventas.get(yIndex);
                    ventasCount++;
                }
            }
            resultado.add(sumaVentasTri/ventasCount);
        }

        return resultado;
    }


    public List<Double> getFactorEstacional(List<Double> promedioTrimestres, Double promedio, List<Double> ventas){

        List<Double> resultado = new ArrayList<>();
        //son 6
        for (Double promedioTri : promedioTrimestres){
            resultado.add(promedioTri/ promedio);
        }

        for (int i = 6; i < ventas.size(); i++){
            resultado.add(resultado.get(i-6));
        }

        return resultado;
    }

    public List<Double> getDemandaDesestacionalizada(List<Double> ventas, List<Double> factoresEstacionales){
        List<Double> resultado = new ArrayList<>();
        for (int i = 0; i < ventas.size(); i++){
            double tempRes = ventas.get(i)/factoresEstacionales.get(i);
            resultado.add(tempRes);

        }
        y = resultado;
        return resultado;
    }

    public List<Double> getPronostico(List<Double> tendenciaList, List<Double> factorEstacionalList){
        List<Double> resultado = new ArrayList<>();
        for (int i = 0; i < tendenciaList.size(); i++){
            resultado.add(tendenciaList.get(i)*factorEstacionalList.get(i));
        }
        return resultado;
    }

    public Double round(Double value){
        return Math.round(value*100.0)/100.0;
    }

}
