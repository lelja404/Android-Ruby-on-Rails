package com.fedorvlasov.lazylist;

import java.net.URL;
import java.util.List; 
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.content.Intent;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.*;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;
import android.view.MenuInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.Bundle;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    ListView list;
    LazyAdapter adapter;
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;
    
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
        adapter=new LazyAdapter(this, name,comments,id,photo_id);
        list.setAdapter(adapter);
        
        Button b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(listener);
        
        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(listener2);
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
    
    public OnClickListener listener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
        	if (bitmap == null) {
				Toast.makeText(getApplicationContext(),
						"Please select image", Toast.LENGTH_SHORT).show();
			} else {
				//uploadimage on server;
			}
        	
        }
    };
    public OnClickListener listener2=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
        	DefaultHttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost("http://10.0.2.2:3000/comments/new");

        // Configure the form parameters
           List <NameValuePair> nvps = new ArrayList <NameValuePair>();
       nvps.add(new BasicNameValuePair("comment[text]", "Third"));
       nvps.add(new BasicNameValuePair("comment[photo_id]", "1"));
       nvps.add(new BasicNameValuePair("comment[user_id]", "1"));


       post.addHeader("Content-Type","application/json");

       try {
                       post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
               } catch (Exception e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }

       HttpResponse response = null;
               try {
                       response = client.execute(post);
               } catch (Exception e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               } 
       HttpEntity entity = response.getEntity();
       System.out.println("form get: " + response.getStatusLine());
       if (entity != null) {
           try {
                               entity.consumeContent();
                       } catch (Exception e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                       }
       }
   
        }
    };
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.imageupload_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.ic_menu_gallery:
			try {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICK_IMAGE);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PICK_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImageUri = data.getData();
				String filePath = null;

				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);

					if (selectedImagePath != null) {
						filePath = selectedImagePath;
					} else if (filemanagerstring != null) {
						filePath = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (filePath != null) {
						//decodeFile(filePath);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
			break;
		default:
		}
	}

	
		

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	
	}

