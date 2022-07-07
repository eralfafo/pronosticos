import java.util.ArrayList;
import java.util.List;

public class PromedioMovil {

    public List<Double> getPromedioMovil(List<Double> ventas){
        List<Double> results = new ArrayList<>();
        Integer index = 0;
        for (Double value: ventas) {
            if(index < 3){
                results.add(0D);
                index += 1;
            }
            else{
                results.add(getLast3MonthsValues(ventas,index));
                index += 1;
            }
        }
        return results;
    }


    public Double getLast3MonthsValues(List<Double> ventas, Integer index){
        Double result = 0D;
        result += ventas.get(index-3);
        result += ventas.get(index-2);
        result += ventas.get(index-1);
        result = result/3;
        return result;
    }
}
