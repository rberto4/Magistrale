
class Record{
	public Object data;   //dati satellite
	public Comparable key;//campo chiave
		
	public Record(){}
	
	public Record(Object d, Comparable k){
		data = d;
		key = k;
	}
	
	public Comparable getKey(){return key;}
	public Object getData(){return data;}
	public String toString(){return "(data:"+data+",key:"+key+") ";}
}