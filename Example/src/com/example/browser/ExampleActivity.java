package com.example.browser;

import java.net.URL;
import com.example.browser.R;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ExampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        
        try {
        TextView name[];
        URL url = new URL("http://10.0.2.2:3000/photos.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
 
        NodeList nodeList = doc.getElementsByTagName("photo");
        name = new TextView[nodeList.getLength()];
        
        for (int i = 0; i < nodeList.getLength(); i++) {

        	Node node = nodeList.item(i);

        	name[i] = new TextView(this);
        	Element fstElmnt = (Element) node;
        	NodeList nameList = fstElmnt.getElementsByTagName("image-file-name");
        	Element nameElement = (Element) nameList.item(0);
        	nameList = nameElement.getChildNodes();
        	name[i].setText("Name = "
        	+ ((Node) nameList.item(0)).getNodeValue());
        	layout.addView(name[i]);
        }
    } catch (Exception e) {
    System.out.println("XML Pasing Excpetion = " + e);
    }
    

setContentView(layout);
}
}