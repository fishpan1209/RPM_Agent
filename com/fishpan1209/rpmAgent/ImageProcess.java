package project3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;


public class ImageProcess {
	
    
	public RavensFigure analyzeImage(String path) {
		
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	Mat img = Highgui.imread(path,0);
    	 if (img == null)
         {
             System.out.println("Failed to load image at " + path);
           
         }
    	
        // Imgproc.adaptiveThreshold(imageBlurr, imageA, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,7, 5);
    	//System.out.println(img);
        //Imgproc.cvtColor(img,img, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.threshold(img, img, 127, 255, Imgproc.THRESH_BINARY_INV);
        /**
    	Mat img = Highgui.imread(path, Imgproc.COLOR_BGR2GRAY);
        Mat imageHSV = new Mat(img.size(), Core.DEPTH_MASK_8U);
        Mat imageBlurr = new Mat(img.size(), Core.DEPTH_MASK_8U);
        Mat imageA = new Mat(img.size(), Core.DEPTH_MASK_ALL);
        Imgproc.cvtColor(img, imageHSV, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imageHSV, imageBlurr, new Size(5,5), 0);
        Imgproc.adaptiveThreshold(imageBlurr, imageA, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,7, 5);
**/
    	
        // Build contours
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(img,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        
        // Saves mapping b/n contours and dict
        
        int[] map = new int[contours.size()];
        
        
        // initiate objectlists
        RavensFigure Figure = new RavensFigure();
        //ArrayList<HashMap<String,String>> objList = new ArrayList<>();

        //iterator contours, save each object as RavensObject, and add attributes to it, 
        //return to an objectList of Figure(Figure.getObjects())
        //for each object in Figure
        for (int i = 0; i < contours.size(); i++) {
            
            MatOfPoint2f contour = new MatOfPoint2f(contours.get(i).toArray());
            MatOfPoint2f approxContour = new MatOfPoint2f();
            Imgproc.approxPolyDP(contour,approxContour,0.01*Imgproc.arcLength(contour, true),true);
            
            //new object
           
            //RavensAttribute attr = new RavensAttribute();
            //RavensObject obj = new RavensObject();
            
            //ArrayList<RavensAttribute> attrList = new ArrayList<RavensAttribute>();
            //String ObjName = null;
            //HashMap<String,String> attr = new HashMap<String,String>();
            
            //temp variables
			
			
            String shape = null;
            String fill = "yes";
            String inside = "none";
            String left_of = "none";
            String above = "none";
            int Size = 0;
            String size = null;
            boolean foundDuplicate = false;
            int calcAngle = 0; //calcAngle = 0 for circle and complex cases
            int sides = approxContour.toArray().length;
            int vertices = 0;
          
            
            RavensObject obj = new RavensObject();
			String ObjName = null;
			ObjName = "Obj"+Integer.toString(i);
			obj.addName(ObjName);
           
            
            //detect circles
            /**
            Mat Circles = new Mat();
            int iMinRadius = 20;
            int iMaxRadius = 400;
           Imgproc.HoughCircles(img, Circles, Imgproc.CV_HOUGH_GRADIENT, 2.0, img.rows() / 8.0, 100.0, 300.0, 
        	         iMinRadius, iMaxRadius);
           if (Circles.cols() > 0)
        	    for (int x = 0; x < Circles.cols(); x++) 
        	        {
        	        double vCircle[] = Circles.get(0,x);

        	        if (vCircle == null)
        	            break;

        	        Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
        	        int radius = (int)Math.round(vCircle[2]);
        	        shape = "circle";
        	        System.out.println("ok,"+shape);
        	        }
           **/
            
            if(sides < 10)
            {
                Imgproc.approxPolyDP(contour,approxContour,0.05*Imgproc.arcLength(contour, true),true);
                calcAngle = findcalcAnglePolygon(approxContour.toArray());
            }
            
            Size = (int)findSize(approxContour.toArray());
            
            Moments M = Imgproc.moments(contour);
            Point centroid = new Point(M.get_m10()/M.get_m00(),M.get_m01()/M.get_m00());
            
  
            // Find basic shapes (More complex shapes TBD)
            if (Size>110 && Size<220) {
            	size = "large";
            	if(sides == 3 )
                {  // if (Imgproc.isContourConvex(contours.get(i))) 
                	//shape = "triangle"
                
                    shape = "triangle";
            
                }
                
                else if(sides == 4)
                {
                    shape = "square";
                }
                else if(sides == 5)
                {
                    shape = "pentagon";
                }
                else if(sides==9)
                {
                    shape = "half-circle";
                }
                
                else if(sides>=11 && sides<14) {
                	shape = "pacman";
                }
                
               
                else if(sides >= 14)
                {
                    shape = "circle";
                }
                else {
                	shape = "TBD";
                }
            }
            else if (Size>50 && Size<110) {
            	size = "medium";
            	if(sides == 3 )
                {  // if (Imgproc.isContourConvex(contours.get(i))) 
                	//shape = "triangle"
                
                    shape = "triangle";
            
                }
                
                else if(sides == 4)
                {
                    shape = "square";
                }
                else if(sides == 5)
                {
                    shape = "pentagon";
                }
                else if(sides==9)
                {
                    shape = "half-circle";
                }
                
                else if(sides==11) {
                	shape = "pacman";
                }
                else if(sides == 12)
                {
                    shape = "plus";
                }
               
                else if(sides > 10)
                {
                    shape = "circle";
                }
                else {
                	shape = "TBD";
                }
            }
            
            else if (Size<50) {
            	size = "small";
            	if(sides == 3 )
                {  // if (Imgproc.isContourConvex(contours.get(i))) 
                	//shape = "triangle"
                
                    shape = "triangle";
            
                }
                
                else if(sides == 4)
                {
                    shape = "square";
                }
                else if(sides == 5)
                {
                    shape = "pentagon";
                }
                else if(sides==9)
                {
                    shape = "half-circle";
                }
               
                else if(sides == 12)
                {
                    shape = "plus";
                }
               
                else if(sides > 10)
                {
                    shape = "circle";
                }
                else {
                	shape = "TBD";
                }
            }
            
            else { foundDuplicate = true; }
            	
            

            RavensAttribute attrShape = new RavensAttribute("shape",shape);
            RavensAttribute attrFill = new RavensAttribute("fill",fill);
            RavensAttribute attrSize = new RavensAttribute("Size",Integer.toString(Size));
            RavensAttribute attrsize = new RavensAttribute("size",size);
            RavensAttribute attrAngle = new RavensAttribute("angle",Integer.toString(calcAngle));
            RavensAttribute attrSides = new RavensAttribute("sides",Integer.toString(sides));
            RavensAttribute attrCentroid = new RavensAttribute("centroid", Integer.toString((int)(centroid.x))+","+Integer.toString((int)(centroid.y)));
          
            obj.addAttrList(attrShape);
            obj.addAttrList(attrFill);
            obj.addAttrList(attrSize);
            obj.addAttrList(attrsize);
            obj.addAttrList(attrAngle);
            obj.addAttrList(attrSides);
            obj.addAttrList(attrCentroid);
            
            
            
            // add attr to RavensAttribute, add attributes to RavensObject
            
			
			//find fill
            
            for (RavensObject currObj: Figure.getObjects())
            {   
            	//RavensAttribute currAttr = new RavensAttribute();
            	String currcentroid = currObj.getAttributeValue("centroid");
            	int centerX = Integer.parseInt(currcentroid.split(",")[0]);
				int centerY = Integer.parseInt(currcentroid.split(",")[1]);
				int centerX0 = (int)centroid.x;
				int centerY0 =  (int)centroid.y;
				//System.out.println(currObj.getName()+">"+centerX+","+centerY+","+currObj.getAttributeValue("Size")+"//" +obj.getName()+centerX0+","+centerY0+","+obj.getAttributeValue("Size"));
				//System.out.println(Math.abs(Integer.parseInt(currObj.getAttributeValue("Size"))-Size)/(double)Size);
				//System.out.println(Size);
				if((currObj.getAttributeValue("shape").equals(shape)) && ((Math.abs(Integer.parseInt(currObj.getAttributeValue("Size"))-Size)/(double)Size) < 0.2)
                		&& Math.abs(centerX-centerX0)<5 && Math.abs(centerY-centerY0)<5)
           
                { //System.out.println(currObj.getAttributeValue("fill"));
					//System.out.println("ok");
                for(RavensAttribute currAttr: currObj.getAttributes()) {
                	if (currAttr.getName()=="fill") {
                		currAttr.changeValue("yes", "no");
                	}
                }
                   
                 
                    foundDuplicate = true;
             
                }
            }
            
			if (!foundDuplicate) {
				
             
                for(RavensObject newObj :Figure.getObjects()) {
    				
    				String newcentroid = newObj.getAttributeValue("centroid");
    	           
    	            int centerX = Integer.parseInt(newcentroid.split(",")[0]);
    				int centerY = Integer.parseInt(newcentroid.split(",")[0]);
    				int centerX0 = (int)centroid.x;
    				int centerY0 =  (int)centroid.y;
    				
    				if ((centerX0 + 40) < centerX) {
    				
    					
    					RavensAttribute attr1 = new RavensAttribute();
    					left_of = newObj.getName();
    					
    					//attr1.addAttribute("left-of", newObj.getName());
    					
    					//obj.addAttrList(attr1);
    				}
    				if ((centerY0 + 40) < centerY) {
    					
    					//System.out.println("ok");
    					RavensAttribute attr2 = new RavensAttribute();
    					above = newObj.getName();
    					
    					//attr2.addAttribute("above", newObj.getName());
    					
    					//obj.addAttrList(attr2);
    				}
    				
    				
    				if(Math.abs(centerX0-centerX)<5 && Math.abs(centerY0-centerY)<5 && Integer.parseInt(newObj.getAttributeValue("Size"))>Size+20) {
                       //System.out.println("ok");
                       RavensAttribute attr3 = new RavensAttribute();
    					inside = newObj.getName();
    					//attr3.addAttribute("inside", newObj.getName());
    					
    					//obj.addAttrList(attr3);
    				}
    				
    				
    			}
                
                
                RavensAttribute attrLeft_of = new RavensAttribute("left-of",left_of);
                RavensAttribute attrAbove = new RavensAttribute("above",above);
                RavensAttribute attrInside = new RavensAttribute("Inside",inside);
                obj.addAttrList(attrLeft_of);
                obj.addAttrList(attrAbove);
                obj.addAttrList(attrInside);
                
                Figure.addObject(obj);
         
            }
			
			//find position 'left-of' & 'above'
			
        }
			
        
/**
		for (int i = 0; i < contours.size(); i++) {
			// left-of and above
			for(RavensObject newObj :Figure.getObjects()) {
				System.out.println(newObj.getName());
				String centroid = newObj.getAttributeValue("centroid");
	            System.out.println("centroid="+centroid);
	            int centerX = Integer.parseInt(centroid.split(",")[0]);
				int centerY = Integer.parseInt(centroid.split(",")[0]);
				int idx = Figure.getObjects().size();
				
			}
			
			
			int idx1 = (int) (hierarchy.get(0, i)[0]);
			int idx2 = (int) (hierarchy.get(0, i)[1]);
			System.out.println(idx1);
			System.out.println(idx2);
			if (idx1 != -1) {
				RavensObject obj1 = Figure.getObjects().get(map[idx1]);
				System.out.println(obj1.getName());
				for (RavensAttribute attr : obj1.getAttributes()) {
					System.out.println(attr.getName() + attr.getValue());

				}
		
				centroid = obj1.getAttributeValue("centroid");
				System.out.println(centroid);
				int centerX1 = Integer.parseInt(centroid.split(",")[0]);
				int centerY1 = Integer.parseInt(centroid.split(",")[0]);

				if ((centerX1 + 40) < centerX) {
					RavensAttribute attr1 = new RavensAttribute();
					System.out.println(centerX1 + 40);
					System.out.println(centerX);
					attr1.addAttribute("left-of", attr1.getName("left-of")
							+ ":" + map[idx1]);
					System.out.println(attr1.getName() + attr1.getValue());
					newObj.addAttrList(attr1);
				}
				if ((centerY1 + 40) < centerY) {
					RavensAttribute attr1 = new RavensAttribute();
					attr1.addAttribute("above", attr1.getValue("above") + ":"
							+ map[idx1]);
					System.out.println(attr1.getName() + attr1.getValue());
					newObj.addAttrList(attr1);

				}

			}

			if (idx2 != -1) {
				RavensObject obj2 = Figure.getObjects().get(map[idx2]);

				centroid = obj2.getAttributeValue("centroid");
				int centerX1 = Integer.parseInt(centroid.split(",")[0]);
				int centerY1 = Integer.parseInt(centroid.split(",")[0]);
				if ((centerX1 + 40) < centerX) {
					RavensAttribute attr2 = new RavensAttribute();
					attr2.addAttribute("left-of", attr2.getName("left-of")
							+ ":" + map[idx2]);
					newObj.addAttrList(attr2);
				}
				if ((centerY1 + 40) < centerY) {
					RavensAttribute attr2 = new RavensAttribute();
					attr2.addAttribute("above", attr2.getName("above") + ":"
							+ map[idx2]);
					newObj.addAttrList(attr2);
				}

			}

			if (newObj.getAttributeValue("left-of") != null) {

				RavensAttribute currAttr = new RavensAttribute();
				currAttr.addAttribute("left-of",
						newObj.getAttributeValue("left-of"));

				newObj.addAttrList(currAttr);
				System.out.println(currAttr.getName() + currAttr.getValue());

			}
			if (newObj.getAttributeValue("above") != null) {
				RavensAttribute currAttr = new RavensAttribute();
				currAttr.addAttribute("above", currAttr.getValue());
				newObj.addAttrList(currAttr);
				System.out.println(currAttr.getName() + currAttr.getValue());

			}

		}
		**/
   
		return Figure;
	}
	

    
    
    public float calcDistance(Point p1, Point p2) {
        return (float)Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2));
    }
    
    public float findSize(Point[] vertexList)
    {
        Float maxDistance = calcDistance(vertexList[0], vertexList[1]);
        Point x = vertexList[0];
        for (Point y: vertexList)
        {
            if(x.equals(y))
            {
                continue;
            }
            float dist = calcDistance(x,y);
            if (maxDistance < dist)
            {
                maxDistance = dist;
            }
        }
        return maxDistance;
    }
    
    public int findcalcAnglePolygon(Point[] vertices)
    {
        int minAngle = 400; //(some random large number which is a bound)
        ArrayList<Integer> calcAngles = new ArrayList<Integer>();//np.array([])
        for (Point x: vertices)
        {   
            ArrayList<PointPair<Point>> dist = new ArrayList<PointPair<Point>>();
            //x = np.array(x_arr[0])
            
            // we have to find the closest two which corresponds to two neighbors
            for (Point y: vertices)
            {
                if (x.equals(y))
                {
                    continue;
                }
                dist.add(new PointPair<Point>(calcDistance(x,y),y));
            }
            Object[] dist_array = (Object [])dist.toArray();
            Arrays.sort(dist_array);
            calcAngles.add(calcAngle(x,((PointPair<Point>)dist_array[0]).getY()));
            calcAngles.add(calcAngle(x,((PointPair<Point>)dist_array[1]).getY()));
        }
        Object [] array = (Object [])calcAngles.toArray();
        Arrays.sort(array);
      //System.out.println((Integer)array[0]);
        return ((Integer)array[0]);
    }

    public int calcAngle(Point p1, Point p2){
 
        return (int)(Math.round(Math.toDegrees(Math.atan2(p1.x - p2.x, p1.y - p2.y))*0.2)/0.2+360)%360;
        
    }
    
    
   
}
    
  