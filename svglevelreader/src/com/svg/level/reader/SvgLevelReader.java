package com.svg.level.reader;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.svg.level.reader.entity.SvgEntity;
import com.svg.level.reader.entity.SvgRect;

public class SvgLevelReader<T> {
	
	private SvgLevelReaderHandler<T> mHandler;
	
	public SvgLevelReader(SvgLevelReaderHandler<T> handler){
		mHandler = handler;
	}

	public SvgLevel<T> loadLevel(String path){
		
		SvgLevel<T> level = new SvgLevel<T>();
		
		ArrayList<T> objects = new ArrayList<T>();
		
		try {
			DocumentBuilderFactory fa = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fa.newDocumentBuilder();
			Document doc = builder.parse(path);
			Element root = doc.getDocumentElement();
			NodeList list = root.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if(node.getNodeName().equals("g")){
					NodeList glist = node.getChildNodes();
					for (int j = 0; j < glist.getLength(); j++) {
						Node gnode = glist.item(j);
						if(gnode.getNodeName().equals("rect")){
							SvgRect rect = new SvgRect();
							parseRectAttri(rect,gnode);
							
							//desc
							parseDesc(rect, gnode);
							
							T o = this.mHandler.handleRect(rect);
							objects.add(o);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		level.objects = objects;
		
		return level;
	}
	
	private void parseRectAttri(SvgRect rect,Node node){
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node it = map.item(i);
			String name = it.getNodeName();
			String value = it.getNodeValue();
			if(name.equals("height")){
				rect.height = Float.valueOf(value);
			}else if(name.equals("width")){
				rect.width = Float.valueOf(value);
			}else if(name.equals("x")){
				rect.x = Float.valueOf(value);
			}else if(name.equals("y")){
				rect.y = Float.valueOf(value);
			}else if(name.equals("transform")){
				if(value.startsWith("matrix")){					
					String numberstr = value.substring("matrix(".length(), value.length()-1);
					String nbs[] = numberstr.split(",");
					float fls[] = new float[nbs.length];
					for (int j = 0; j < fls.length; j++) {
						fls[j] = Float.valueOf(nbs[i]);
					}
					rect.matrix = fls;
				}
			}
		}
	}
	
	private void parseDesc(SvgEntity entity,Node node){
		if(node.hasChildNodes()){
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node item = list.item(i);
				if(item.getNodeName().equals("desc")){
					HashMap<String, String> map = new HashMap<String, String>(); 
					String desc = item.getTextContent();
					String lines[] = desc.split("\n");
					for (int j = 0; j < lines.length; j++) {
						String line = lines[j];
						String pairs[] = line.split("=");
						map.put(pairs[0],pairs[1]);
					}
					entity.desc = map;
				}
			}
		}
	}
	
	public static <T> void printf(T t){
		System.out.print(t+"\n");
	}
}
