import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ExcelProcessing {

    String filePath = "";
    String filename = "";
    File file;
    public Integer processData() throws IOException {

        try {
            if (!filePath.equals("")){
                LineChartCreator lineChartCreator = new LineChartCreator();
                lineChartCreator.createChartWithFile(filePath);
                return 0;
            }
            else {
                return -1;
            }
        }catch (Exception e){
            throw e;
        }
    }

    public String searchFile(){
        ExcelNameReader excelNameReader = new ExcelNameReader();
        file = excelNameReader.getFile();
        filePath = file.getAbsolutePath();
        filename = file.getName();
        return filename;
    }
}
