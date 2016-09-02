package project3;

public class AttributeDiff extends Pair<RavensAttribute, RavensAttribute>{
	
	private static RavensAttribute NON_EXIST_ATTR = new RavensAttribute("non-exist-attribute", "");

	private String attributeName;
	
	public AttributeDiff(String attributeName, RavensAttribute attr1, RavensAttribute attr2) {
		super(attr1, attr2);
		if(attr1 == null) {
			setFirst(NON_EXIST_ATTR);
		}
		if(attr2 == null) {
			setSecond(NON_EXIST_ATTR);
		}
		this.attributeName = attributeName;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public double getWeightedDifference() {
		if(isDifferent()) {
			return getWeight();
		}
		return 0;
	}
	
	public double getWeight() {
	
		return 1.0;
	}
	
	//two attrs have different values
	//exceptions: fill add up(basic 11), angle flip(basic 13)，inside transferable(basic 20)
	public boolean isDifferent() {
		if (getFirst().getName()=="centroid" || getFirst().getName()=="sides" || getFirst().getName()=="Size") {return false;}
		
	    else if (getFirst().getValue().equals(getSecond().getValue())) {
			return false;
		    }
	    else return true;
	}
	
	public boolean isDifferentFrom(AttributeDiff another) {
		
		if (!isDifferent() && !another.isDifferent()) //different transformaiton between attrs
		{
			
			return !getAttributeName().equals(another.getAttributeName());
		}
		 
		 
		 
			   
		 else return !getAttributeName().equals(another.getAttributeName()) 
				|| !getFirst().getValue().equals(another.getFirst().getValue())
				|| !getSecond().getValue().equals(another.getSecond().getValue());
	}
	
}

	

