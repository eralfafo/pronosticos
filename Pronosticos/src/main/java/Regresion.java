import java.util.ArrayList;
import java.util.List;

public class Regresion {
    //periodo = x
    //ventas = y
    private List<Double> x;
    private List<Double> y;
    private int n;          //número de datos
    public double a, b, m;    //pendiente y ordenada en el origen
    public Regresion(List<Double> x, List<Double> y) {
        this.x=x;
        this.y=y;
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

    public List<Double> getPronostico(){
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

}