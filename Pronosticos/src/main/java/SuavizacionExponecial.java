import java.util.ArrayList;
import java.util.List;

public class SuavizacionExponecial {

    List<Double> results = new ArrayList<>();
    public List<Double> getSuavizacionExponencial(List<Double> ventas, Double alpha){

        Integer index = 0;
        for (Double value: ventas) {
            if(index < 1){
                results.add(ventas.get(index));
                index += 1;
            }
            else{
                results.add(getPronostico(ventas,alpha,index));
                index += 1;
            }
        }
        return results;
    }


    public Double getPronostico(List<Double> ventas, Double alpha,Integer index){
        Double result = 0D;
        result += alpha * ventas.get(index-1);
        result += (1-alpha) * results.get(index-1);
        return result;
    }

}
