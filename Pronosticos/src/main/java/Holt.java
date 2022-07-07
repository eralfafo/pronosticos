import java.util.ArrayList;
import java.util.List;

public class Holt {

    List<Double> results = new ArrayList<>();
    public List<Double> getHolt(List<Double> ventas, Double alpha, Double beta){

        Integer index = 0;
        List<Double> atList = new ArrayList<>();
        List<Double> ttList = new ArrayList<>();

        for (Double value: ventas) {
            if(index < 1){
                atList.add(ventas.get(index));
                ttList.add(0D);
                results.add(0D);
                index += 1;
            }
            else{
                atList.add(getAtValue(ventas,alpha,index,atList,ttList));
                ttList.add(getTtValue(ventas,alpha,beta,index,atList,ttList));
                results.add(getPronostico(ventas,atList,ttList,index));
                index += 1;
            }
        }
        return results;
    }


    public Double getAtValue(List<Double> ventas, Double alpha,Integer index, List<Double> atList, List<Double> ttList){
        Double result = 0D;
        result += alpha * ventas.get(index);
        result += (1-alpha) * (atList.get(index-1) + ttList.get(index-1));
        return result;
    }

    public Double getTtValue(List<Double> ventas, Double alpha,Double beta, Integer index, List<Double> atList, List<Double> ttList){
        Double result = 0D;
        result += beta * (atList.get(index) - atList.get(index-1));
        result += (1-beta) * (ttList.get(index-1));
        return result;
    }

    public Double getPronostico(List<Double> ventas, List<Double> atList, List<Double> ttList,Integer index){
        Double result = 0D;
        result += atList.get(index-1) + ttList.get(index-1);
        return result;
    }
}
