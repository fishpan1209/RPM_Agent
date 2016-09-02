package project3;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectDiff extends Pair<RavensObject, RavensObject> {
	
	private HashMap<String, AttributeDiff> differences;
	
	public ObjectDiff(RavensObject first, RavensObject second) {
		super(first, second);
	}
	
	public HashMap<String, AttributeDiff> getDifferences() {
		if (differences != null) {
			return differences;
		}
		differences = new HashMap<>();
		HashMap<String, RavensAttribute> set1Attributes = new HashMap<>();
		for (RavensAttribute attr1 : getFirst().getAttributes()) {
			set1Attributes.put(attr1.getName(), attr1);
		}
		for (RavensAttribute attr2 : getSecond().getAttributes()) {
			String attrName = attr2.getName();
			differences.put(attrName, 
					new AttributeDiff(attrName, set1Attributes.get(attrName), attr2));
			set1Attributes.remove(attrName);
		}
		for (Map.Entry<String, RavensAttribute> remaining : set1Attributes.entrySet()) {
			differences.put(remaining.getKey(), 
					new AttributeDiff(remaining.getKey(), remaining.getValue(), null));
		}
		return differences;
	}
	
	
	public int getAffinity() {
		HashMap<String, AttributeDiff> differences = getDifferences();
	

		if (differences.containsKey("shape")) {
			AttributeDiff shapeDiff = differences.get("shape");
			if (shapeDiff.getWeightedDifference() > 0) {
				// Different shape
				return 0;
				
			}
		} else {
			return 0;
		}
		
		if (differences.containsKey("size")) {
			AttributeDiff sizeDiff = differences.get("size");
			if (sizeDiff.getWeightedDifference() > 0) {
				//same shape, different size
				return 1;
			}

		}
		
		if (differences.containsKey("fill")) {
			AttributeDiff fillDiff = differences.get("fill");
			if (fillDiff.getWeightedDifference() > 0) {
				// Same shape, same size,different filling.
				return 4;//was 2
			}
		}
		
		if (differences.containsKey("angle")) {
			AttributeDiff angleDiff = differences.get("angle");
			if (angleDiff.getWeightedDifference() > 0) {
                
				return 4;
			}
		}
		/**
		if (differences.containsKey("vertical-flip")) {
			AttributeDiff reflectionDiff = differences.get("vertical-flip");
			if (reflectionDiff.getWeightedDifference() > 0) {
				//same shape, size, filling, flipped
				return 5;
			}
		}
		
		**/
		
		
		if (differences.containsKey("inside")) {
			AttributeDiff reflectionDiff = differences.get("inside");
			if (reflectionDiff.getWeightedDifference() > 0) {
				return 6;
			}
		}
		
		if (differences.containsKey("left-of")) {
			AttributeDiff reflectionDiff = differences.get("left-of");
			if (reflectionDiff.getWeightedDifference() > 0) {
				return 6;
			}
		}
		
		if (differences.containsKey("above")) {
			AttributeDiff reflectionDiff = differences.get("above");
			if (reflectionDiff.getWeightedDifference() > 0) {
				return 6;
			}
		}
		
	
		
		
		//System.out.println(affinity);
		return 10;
	}

}
