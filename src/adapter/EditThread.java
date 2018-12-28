package adapter;

public interface EditThread{
	public void editAuto(int ops, String [] input);
	public void editOptionSetName(String modelKey, String opSetName, String updateName);
	public void editOptionName(String modelKey, String opSetName, String optionName, String updateName);
	public void editOptionPrice(String modelKey, String opSetName, float oldPrice, float updatePrice);
}
