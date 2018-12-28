package adapter;

public interface UpdateAuto {
	public void updateOptionSetName(String Modelname, String OptionSetname, String newName, String key);
	public void updateOptionPrice(String modelName, String OptionSetName, float oldprice, float newPrice, String key);
}
