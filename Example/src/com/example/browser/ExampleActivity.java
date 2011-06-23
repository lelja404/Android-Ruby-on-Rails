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
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;



public class ExampleActivity extends Activity {
	ListView list;
    LazyAdapter adapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String name[];
        Integer id[];
        String comments[];
        Integer photo_id[];
       
        try {
            
            
            URL url = new URL("http://10.0.2.2:3000/photos.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
     
            NodeList nodeList = doc.getElementsByTagName("photo");
            name = new String[nodeList.getLength()];
            id=new Integer[nodeList.getLength()];
            for (int i = 0; i < nodeList.getLength(); i++) {

            	Node node = nodeList.item(i);

            	
            	Element fstElmnt = (Element) node;
            	NodeList nameList = fstElmnt.getElementsByTagName("image-file-name");
            	NodeList idList = fstElmnt.getElementsByTagName("id");

            	Element nameElement = (Element) nameList.item(0);
            	Element idElement = (Element) idList.item(0);

            	nameList = nameElement.getChildNodes();
            	idList = idElement.getChildNodes();

            	name[i]="http://10.0.2.2:3000/system/images/"+((Node) idList.item(0)).getNodeValue()+"/original/"+((Node) nameList.item(0)).getNodeValue();
            	id[i]=Integer.parseInt(((Node) idList.item(0)).getNodeValue());
            }
            	 URL url1 = new URL("http://10.0.2.2:3000/comments.xml");
                 DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
                 DocumentBuilder db1 = dbf1.newDocumentBuilder();
                 Document doc1 = db1.parse(new InputSource(url1.openStream()));
                 doc1.getDocumentElement().normalize();
          
                 NodeList nodeList1 = doc1.getElementsByTagName("comment");
                 comments = new String[nodeList1.getLength()];
                 photo_id=new Integer[nodeList1.getLength()];
                 for (int k = 0; k < nodeList1.getLength(); k++) {

                 	Node node1 = nodeList1.item(k);

                 	
                 	Element fstElmnt1 = (Element) node1;
                 	NodeList commentsList = fstElmnt1.getElementsByTagName("text");
                 	NodeList photo_idList = fstElmnt1.getElementsByTagName("photo-id");

                 	Element commentsElement = (Element) commentsList.item(0);
                 	Element photo_idElement = (Element) photo_idList.item(0);

                 	commentsList = commentsElement.getChildNodes();
                 	photo_idList = photo_idElement.getChildNodes();

                 	comments[k]=((Node) commentsList.item(0)).getNodeValue();
                 	photo_id[k]=Integer.parseInt(((Node) photo_idList.item(0)).getNodeValue());

            	
            }
       	
                 
        list=(ListView)findViewById(R.id.list);
        adapter=new LazyAdapter(this,name,comments,id,photo_id);
        list.setAdapter(adapter);
        
        Button b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(listener);

        }
        catch (Exception ex)
        {}
}
    @Override
    public void onDestroy()
    {
        adapter.imageLoader.stopThread();
        list.setAdapter(null);
        super.onDestroy();
    }
    public OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            
        }
    };
}   
  