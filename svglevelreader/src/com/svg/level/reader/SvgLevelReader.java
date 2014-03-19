package com.svg.level.reader;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.svg.level.reader.entity.SvgRect;

public class SvgLevelReader<T> {
	
	private SvgLevelReaderHandler<T> mHandler;
	
	public SvgLevelReader(SvgLevelReaderHandler<T> handler){
		mHandler = handler;
	}

	public ArrayList<T> loadLevel(String path){
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
		
		return objects;
	}
}
