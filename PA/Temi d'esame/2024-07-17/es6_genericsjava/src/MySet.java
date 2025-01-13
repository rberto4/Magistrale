public class MySet<T>{
    private Object[] wArray;

    public MySet (T[] array){
        this.wArray = new Object[0];

        for (int i = 0; i<array.length; i++ ){
            if (!isAlredyPresent(this.wArray,array[i])){
                Object[] temp = new Object[wArray.length + 1];
                temp[temp.length - 1] = array[i];
                System.arraycopy(this.wArray, 0, temp, 0, this.wArray.length);
                this.wArray = temp;
            }
        }
    }

    public Object[] getArray(){
        return wArray;
    }

    public MySet unionSet(MySet s1, MySet s2){
        MySet s = new MySet(
           new Object[ s1.getArray().length]
        );

        for(int i = 0; i<s1.wArray.length; i++){
            s.wArray[i] = s1.wArray[i];
        } 

        for (int i = 0; i<s2.getArray().length; i++ ){
            if (!isAlredyPresent(s.wArray, s2.wArray[i] )){
                Object[] temp = new Object[s.wArray.length + 1];
                temp[temp.length - 1] = s2.wArray[i];
                System.arraycopy(s2.wArray, 0, temp, 0, s.wArray.length);
                s.wArray = temp;
            }
        }

        return s;
    }

    private boolean isAlredyPresent (Object[] array ,Object value){
            for (int i=0; i<array.length; i++){
                if (array[i] == value){
                return true;
            }
        }
        return false;
    } 

    public void printSet(){
        System.out.println("Stampo il set : ");
        for (int i = 0; i<wArray.length;i++){
            System.out.println(wArray[i] + " ");
        }
    }

}