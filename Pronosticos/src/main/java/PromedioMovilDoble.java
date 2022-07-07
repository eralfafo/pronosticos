import java.util.ArrayList;
import java.util.List;

public class PromedioMovilDoble {


    public List<Double> getPromedioMovilDoble(List<Double> ventas){
        List<Double> mts = new ArrayList<>();
        List<Double> mtps = new ArrayList<>();
        List<Double> aList = new ArrayList<>();
        List<Double> bList = new ArrayList<>();
        List<Double> results = new ArrayList<>();

        Integer index = 0;

        //get MTs
        for (Double value: ventas) {
            if(index < 6){
                mts.add(0D);
                index += 1;
            }
            else{
                mts.add(processWithLast6(ventas,index));
                index += 1;
            }
        }

        //get MTs'
        index = 0;
        for (Double value: mts) {
            if(index < 12){
                mtps.add(0D);
                index += 1;
            }
            else{
                mtps.add(processWithLast6(mts,index));
                index += 1;
            }
        }

        //get a
        index = 0;
        for (Double value: mtps) {
            if(index < 12){
                aList.add(0D);
                index += 1;
            }
            else{
                aList.add(processAValue(mts,mtps,index));
                index += 1;
            }
        }

        //get b
        index = 0;
        for (Double value: mtps) {
            if(index < 12){
                bList.add(0D);
                index += 1;
            }
            else{
                bList.add(processBValue(mts,mtps,index));
                index += 1;
            }
        }

        //get pronostico
        index = 0;
        for (Double value: mtps) {
            if(index < 13){
                results.add(0D);
                index += 1;
            }
            else{
                results.add(getPronosticValue(aList,bList,index));
                index += 1;
            }
        }



        return results;
    }

    public Double processWithLast6(List<Double> ventas, Integer index){
        Double result = 0D;
        result += ventas.get(index-6);
        result += ventas.get(index-5);
        result += ventas.get(index-4);
        result += ventas.get(index-3);
        result += ventas.get(index-2);
        result += ventas.get(index-1);
        result += ventas.get(index);
        result = result/7;
        return result;
    }

    public Double processAValue(List<Double> mts,List<Double> mtps, Integer index){
        Double result = 0D;
        result = (mts.get(index)*2)-(mtps.get(index));
        return result;
    }

    public Double processBValue(List<Double> mts,List<Double> mtps, Integer index){
        Double result = 0D;
        result = mts.get(index)-mtps.get(index);
        return result;
    }

    public Double getPronosticValue(List<Double> aList,List<Double> bList, Integer index){
        Double result = 0D;
        result = aList.get(index-1)+bList.get(index-1);
        return result;
    }

}
