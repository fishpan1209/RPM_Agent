package project3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class FigureDiff extends Pair<RavensFigure, RavensFigure> {

	private ArrayList<ObjectDiff> differences;
	//private LinkTransform transform;
	
	public FigureDiff(RavensFigure fig1, RavensFigure fig2) {
		super(fig1, fig2);
		init();
	}
	
	public ArrayList<ObjectDiff> getDifferences() {
		return differences;
	}
	
    
	public double similarity(FigureDiff AB, FigureDiff CX) {
		HashSet<AttributeDiff> attrDiffs1 = new HashSet<>();
		for (ObjectDiff objDiff1 : AB.differences) {
			for (AttributeDiff attrDiff1 : objDiff1.getDifferences().values()) {
				
				attrDiffs1.add(attrDiff1);
				
			}
		}
		double matchedSize = 0;
		double diffSize = 0;

		for (ObjectDiff objDiff2 : CX.differences) {
			for (AttributeDiff attrDiff2 : objDiff2.getDifferences().values()) {
				AttributeDiff matchedAttrDiff = null;
				for (AttributeDiff attrDiff1 : attrDiffs1) {
					if (!attrDiff1.isDifferentFrom(attrDiff2)) {
						matchedAttrDiff = attrDiff1;
						break;
					}// if all the attrDiffs are the same, return size
				}
				if (matchedAttrDiff != null) {
					++matchedSize;
					attrDiffs1.remove(matchedAttrDiff);
				} else {
					// figureDiff in differences
					
				}
					++diffSize;

				}
			}// remove same attrDiffs in two obj map, record different ones and
				// matched ones
		diffSize += attrDiffs1.size();
		
          for (ObjectDiff objDiff2 : CX.getDifferences()) {
			
			for (AttributeDiff attrdiff2 : objDiff2.getDifferences().values()) {
				//System.out.println(attrdiff2.getFirst().getName()+":"+attrdiff2.getFirst().getValue()+
					//	"//"+attrdiff2.getSecond().getName()+":"+attrdiff2.getSecond().getValue());
				
				if (attrdiff2.getFirst().getName()=="Size" || attrdiff2.getFirst().getName()=="centroid" ||attrdiff2.getFirst().getName()=="sides") {
					diffSize = diffSize-1;
				}
				else if (attrdiff2.getFirst().getName()=="shape") {
					diffSize = diffSize+1;
				}
				else if (attrdiff2.getFirst().getName()=="size" || attrdiff2.getFirst().getName()=="fill") {
					diffSize = diffSize+0.5;
				}
				/**
				else if (attrdiff2.getFirst().getName()=="angle") {
					for(AttributeDiff attrdiff1 " objDiff)
					if (Integer.parseInt(attrdiff2.getFirst().getValue())-Integer.parseInt(attrdiff2.getSecond().getValue())
							-Integer.parseInt(attrdiff1.getFirst().getValue())
				}
				**/
				
			}
			//System.out.println("__________________");
		}
		
			
			// Jaccard set similarity metric.
		
			System.out.println(diffSize+"/"+matchedSize);
			
			
			return (double) matchedSize / (diffSize + matchedSize);
		}

	public double similarity2x2(FigureDiff AB,FigureDiff AC, FigureDiff CX, FigureDiff BX) {
	double similarityAB=similarity(AB, CX);
	double similarityAC= similarity(AC,BX);
	
	return (similarityAB+similarityAC)*(similarityAB+similarityAC);
		
		/**
		HashSet<AttributeDiff> attrDiffs1 = new HashSet<>();
		for (ObjectDiff objDiff1 : AB.differences) {
			for (AttributeDiff attrDiff1 : objDiff1.getDifferences().values()) {
				attrDiffs1.add(attrDiff1);
			}
		}
		
		
		double matchedSize=0;
		double diffSize = 0;
	
		
		for (ObjectDiff objDiff2 : CX.differences) {
			for (AttributeDiff attrDiff2 : objDiff2.getDifferences().values()) {
				AttributeDiff matchedAttrDiff = null;
				for (AttributeDiff attrDiff1 : attrDiffs1) {
					if (!attrDiff1.isDifferentFrom(attrDiff2)) {
						matchedAttrDiff = attrDiff1;
						break;
					}//if all the attrDiffs are the same, return size
				}
				if (matchedAttrDiff != null) {
					++matchedSize;
					attrDiffs1.remove(matchedAttrDiff);
				} else {
					//figureDiff in differences
					++diffSize;
		
				}
			}//remove same attrDiffs in two obj map, record different ones and matched ones
		}
		
		for (ObjectDiff objDiff2 : BX.differences) {
			for (AttributeDiff attrDiff2 : objDiff2.getDifferences().values()) {
				AttributeDiff matchedAttrDiff = null;
				for (AttributeDiff attrDiff1 : attrDiffs1) {
					if (!attrDiff1.isDifferentFrom(attrDiff2)) {
						matchedAttrDiff = attrDiff1;
						break;
					}//if all the attrDiffs are the same, return size
				}
				if (matchedAttrDiff != null) {
					++matchedSize;
					attrDiffs1.remove(matchedAttrDiff);
				} else {
					//figureDiff in differences
					++diffSize;
		
				}
			}//remove same attrDiffs in two obj map, record different ones and matched ones
		}
		
		
		HashSet<AttributeDiff> attrDiffs3 = new HashSet<>();
		for (ObjectDiff objDiff3 : AC.differences) {
			for (AttributeDiff attrDiff3 : objDiff3.getDifferences().values()) {
				attrDiffs3.add(attrDiff3);
			}
		}
		for (ObjectDiff objDiff4 : BX.differences) {
			for (AttributeDiff attrDiff4 : objDiff4.getDifferences().values()) {
				AttributeDiff matchedAttrDiff = null;
				for (AttributeDiff attrDiff3 : attrDiffs3) {
					if (!attrDiff4.isDifferentFrom(attrDiff3)) {
						matchedAttrDiff = attrDiff3;
						break;
					}//if all the attrDiffs are the same, return size
				}
				if (matchedAttrDiff != null) {
					++matchedSize;
					attrDiffs1.remove(matchedAttrDiff);
				} else {
					//figureDiff in differences
					++diffSize;
		
				}
			}//remove same attrDiffs in two obj map, record different ones and matched ones
		}
		
		diffSize += attrDiffs1.size();
		return (double) matchedSize / (matchedSize + diffSize); 
		
		**/
		
	}
	
	
	
	private void init() {
		ArrayList<ObjectDiff> objPairwiseDiffs = new ArrayList<>();
		for (RavensObject obj1 : getFirst().getObjects()) {
			for (RavensObject obj2 : getSecond().getObjects()) {
				objPairwiseDiffs.add(new ObjectDiff(obj1, obj2));
			}
		}
		objPairwiseDiffs.sort(new Comparator<ObjectDiff>() {

			@Override
			public int compare(ObjectDiff objDiff1, ObjectDiff objDiff2) {
				// The better affiliated, the smaller.
				return objDiff2.getAffinity() - objDiff1.getAffinity();
			}
		});
		// Now that the objPairwiseDiffs array is sorted descendingly
		// according to the affinity value of object pairs.
		
		differences = new ArrayList<>();
		HashSet<RavensObject> objInMatching = new HashSet<>();
		for (ObjectDiff objDiff : objPairwiseDiffs) {
			RavensObject o1 = objDiff.getFirst();
			RavensObject o2 = objDiff.getSecond();
			if (!objInMatching.contains(o1) && !objInMatching.contains(o2)) {
				differences.add(objDiff);
				objInMatching.add(o1);
				objInMatching.add(o2);
			}
		}
		for (ObjectDiff objDiff : objPairwiseDiffs) {
			RavensObject o1 = objDiff.getFirst();
			RavensObject o2 = objDiff.getSecond();
			if (!objInMatching.contains(o1) || !objInMatching.contains(o2)) {
				differences.add(objDiff);
				objInMatching.add(o1);
				objInMatching.add(o2);
			}
		}
	}   
}
