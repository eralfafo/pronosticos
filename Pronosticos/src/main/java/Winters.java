import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Winters {

    List<Double> results = new ArrayList<>();
    public List<List<Double>> getWinters(List<Double> ventas, Double alpha, Double beta, Double gamma, Integer groupCount){

        Integer index = 0;
        List<Double> ytList = new ArrayList<>();
        List<Double> atList = new ArrayList<>();
        List<Double> ttList = new ArrayList<>();
        List<Double> stList = new ArrayList<>();

        for (int i = index; i < ventas.size(); i += groupCount){
            int limit = 0;
            Double tempResult = 0D;
            for (int y = i; limit < groupCount && y < ventas.size(); y++){
                tempResult += ventas.get(y);
                limit++;
            }
            ytList.add(tempResult);
        }

        for (Double value: ytList) {
            if(index < 1){
                atList.add(ytList.get(index));
                ttList.add(0D);
                stList.add(1D);
                results.add(0D);
                index += 1;
            }
            else{
                atList.add(getAtValue(alpha,ytList,stList,atList,ttList,index));
                ttList.add(getTtValue(beta,atList,ttList,index));
                stList.add(getstValue(gamma,ytList,atList,stList,index));
                results.add(getPronostico(atList,ttList,stList,index));
                index += 1;
            }
        }

        List<List<Double>> res = new ArrayList<>();
        res.add(ytList);
        res.add(results);
        return res;
    }


    public Double getAtValue(Double alpha, List<Double> ytList,
                             List<Double> stList, List<Double> atList,List<Double> ttList
                            , Integer index){

        Double result = 0D;
        Double stValue = 0D;
        if(index - 2 < 0){
            stValue = 1.00D;
        }else{
            stValue = stList.get(index - 2);
        }
        result += alpha * (ytList.get(index)/stValue);
        result += (1-alpha) * (atList.get(index-1) + ttList.get(index-1));

        return result;
    }

    public Double getTtValue(Double beta, List<Double> atList, List<Double> ttList, Integer index){

        Double result = 0D;


        result += beta * (atList.get(index) - atList.get(index-1));
        result += (1 - beta) * ttList.get(index-1);
        return result;
    }

    public Double getstValue(Double gamma, List<Double> ytList, List<Double> atList,
                             List<Double> stList,Integer index){

        Double result = 0D;
        Double stValue = 0D;
        if(index - 2 < 0){
            stValue = 1.00D;
        }else{
            stValue = stList.get(index - 2);
        }

        result += gamma * (ytList.get(index)/atList.get(index));
        result += (1 - gamma) * stValue;
        return result;
    }

    public Double getPronostico(List<Double> atList, List<Double> ttList, List<Double> stList, Integer index){
        Double result = 0D;
        Double stValue = 0D;
        if(index - 2 < 0){
            stValue = 1.00D;
        }else{
            stValue = stList.get(index - 2);
        }

        result += (atList.get(index-1) + ttList.get(index-1)) * stValue;
        return result;
    }

}




