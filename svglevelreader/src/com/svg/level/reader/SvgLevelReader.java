package com.svg.level.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.svg.level.reader.entity.Svg;
import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgEntity;
import com.svg.level.reader.entity.SvgG;
import com.svg.level.reader.entity.SvgPath;
import com.svg.level.reader.entity.SvgPolygon;
import com.svg.level.reader.entity.SvgRect;
import com.svg.level.reader.entity.SvgSizeEntity;

public class SvgLevelReader {

	private HashMap<String, ArrayList<SvgEntity>> cache = new HashMap<String, ArrayList<SvgEntity>>();

	public SvgLevelReader() {
	}

	/**
	 * call level handler's hanle method to generate game objects.
	 * 
	 * @param path
	 * @return
	 */
	public void loadLevel(SvgLevelReaderHandler handler, String path) {

		if (!cache.containsKey(path)) {
			return;
		}

		ArrayList<SvgEntity> cacehSvgObjs = cache.get(path);

		SvgEntityCacheMap map = new SvgEntityCacheMap(cacehSvgObjs);

		for (int i = 0; i < cacehSvgObjs.size(); i++) {
			SvgEntity entity = cacehSvgObjs.get(i);
			entity.map = map;

			if (entity instanceof SvgRect) {
				handler.handleRect((SvgRect) entity);
			} else if (entity instanceof Svg) {
				handler.handleSvg((Svg) entity);
			} else if (entity instanceof SvgCircle) {
				handler.handleCircle((SvgCircle) entity);
			} else if (entity instanceof SvgPath) {
				handler.handlePath((SvgPath) entity);
			} else if (entity instanceof SvgPolygon){
				handler.handlePolygon((SvgPolygon)entity);
			}
		}

	}

	/**
	 * parse xml to svgentity.
	 * 
	 * @param input
	 * @param path
	 */
	public void initLevel(InputStream input, String path) {
		try {
			DocumentBuilderFactory fa = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fa.newDocumentBuilder();
			Document doc = builder.parse(input);
			init(doc, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initLevel(String path) {
		try {
			DocumentBuilderFactory fa = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fa.newDocumentBuilder();
			Document doc = builder.parse(path);
			init(doc, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(Document doc, String path) {
		ArrayList<SvgEntity> objects = new ArrayList<SvgEntity>();
		try {
			Element root = doc.getDocumentElement();

			Svg svg = new Svg();
			// parse block width and height
			parseRoot(root, svg);
			objects.add(svg);

			NodeList list = root.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeName().equals(Const.TAG_G)) {

					objects.add(parseG(node));

					NodeList glist = node.getChildNodes();
					for (int j = 0; j < glist.getLength(); j++) {
						Node gnode = glist.item(j);

						if (gnode.getNodeName().equals(Const.TAG_RECT)) {
							objects.add(parseRect(gnode));
						} else if (gnode.getNodeName().equals(Const.TAG_CIRCLE)) {
							objects.add(parseCircle(gnode));
						} else if (gnode.getNodeName().equals(Const.TAG_PATH)) {
							objects.add(parsePath(gnode));
						} else if (gnode.getNodeName().equals(Const.TAG_POLYGON)){
							objects.add(parsePolygon(gnode));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cache.put(path, objects);
	}

	private SvgG parseG(Node node) {
		SvgG g = new SvgG();

		return g;
	}
	
	private SvgPolygon parsePolygon(Node node){
		SvgPolygon polygon = new SvgPolygon();
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node attr = map.item(i);
			if(attr.getNodeName().equals(Const.ATTR_POINTS)){
				String value = attr.getNodeValue();
				String pstr[] = value.split(" ");
				
				float points[] = new float[pstr.length * 2];
				for (int j = 0; j < pstr.length; j++) {
					String pair = pstr[j];
					String tmp[] = pair.split(",");
					points[j*2] = Float.valueOf(tmp[0]);
					points[j*2+1] = Float.valueOf(tmp[1]);
				}
				polygon.points = points;
			}else if(parseAttrId(polygon, attr)){
				continue;
			}
		}
		
		parseDesc(polygon, node);
		
		return polygon;
	}

	private SvgRect parseRect(Node node) {
		SvgRect rect = new SvgRect();
		parseRectAttri(rect, node);
		// desc
		parseDesc(rect, node);
		return rect;
	}

	private SvgCircle parseCircle(Node node) {
		SvgCircle circle = new SvgCircle();
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node it = attrs.item(i);
			if (it.getNodeName().equals(Const.ATTR_CX)) {
				circle.x = Float.valueOf(it.getNodeValue());
			} else if (it.getNodeName().equals(Const.ATTR_CY)) {
				circle.y = Float.valueOf(it.getNodeValue());
			} else if (it.getNodeName().equals(Const.ATTR_R)) {
				circle.r = Float.valueOf(it.getNodeValue());
			} else if (it.getNodeName().equals(Const.ATTR_TRANSFORM)) {
				if (parseFuncTranslate(circle, it)) {
					continue;
				}
			} else if (parseAttrId(circle, it)) {
				continue;
			}
		}

		parseDesc(circle, node);

		return circle;
	}

	private SvgPath parsePath(Node node) {
		SvgPath path = new SvgPath();

		NamedNodeMap map = node.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node attr = map.item(i);
			if (attr.getNodeName().equals(Const.ATTR_D)) {
				String value = attr.getNodeValue();
				String numberstr = value.substring("m ".length(),
						value.length());
				String nbpairs[] = numberstr.split(" ");
				float d[] = new float[nbpairs.length * 2];
				for (int j = 0; j < nbpairs.length; j++) {
					String pair = nbpairs[j];
					String tmp[] = pair.split(",");
					d[j*2] = Float.valueOf(tmp[0]);
					d[j*2+1] = Float.valueOf(tmp[1]);
				}
				path.d = d;
			}
			
		}

		parseDesc(path, node);
		
		return path;
	}

	private void parseRoot(Element root, Svg svg) {
		NamedNodeMap map = root.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node node = map.item(i);
			if (parseAttrHeight(svg, node)) {
				continue;
			} else if (parseAttrWidth(svg, node)) {
				continue;
			}
		}
	}

	private void parseRectAttri(SvgRect rect, Node node) {
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node it = map.item(i);
			String name = it.getNodeName();
			String value = it.getNodeValue();
			if (name.equals(Const.ATTR_HEIGHT)) {
				rect.height = Float.valueOf(value);
			} else if (name.equals(Const.ATTR_WIDTH)) {
				rect.width = Float.valueOf(value);
			} else if (name.equals(Const.ATTR_X)) {
				rect.x = Float.valueOf(value);
			} else if (name.equals(Const.ATTR_Y)) {
				rect.y = Float.valueOf(value);
			} else if (name.equals(Const.ATTR_TRANSFORM)) {
				if (value.startsWith("matrix")) {
					String numberstr = value.substring("matrix(".length(),
							value.length() - 1);
					String nbs[] = numberstr.split(",");
					float fls[] = new float[nbs.length];
					for (int j = 0; j < fls.length; j++) {
						fls[j] = Float.valueOf(nbs[j]);
					}
					rect.matrix = fls;
				} else if (parseFuncTranslate(rect, it)) {
					continue;
				}
			} else if (parseAttrId(rect, it)) {
				continue;
			}
		}
	}

	private boolean parseFuncTranslate(SvgEntity entity, Node attr) {
		String value = attr.getNodeValue();
		if (value.startsWith(Const.FUNC_TRANSLATE)) {
			String numberstr = value.substring(
					(Const.FUNC_TRANSLATE + "(").length(), value.length() - 1);
			String nbs[] = numberstr.split(",");
			entity.translateX = Float.valueOf(nbs[0]);
			entity.translateY = Float.valueOf(nbs[1]);
			return true;
		}
		return false;
	}

	private boolean parseAttrWidth(SvgSizeEntity entity, Node node) {
		if (node.getNodeName().equals(Const.ATTR_WIDTH)) {
			String value = node.getNodeValue();
			entity.width = Float.valueOf(value);
			return true;
		}
		return false;
	}

	private boolean parseAttrHeight(SvgSizeEntity entity, Node node) {
		if (node.getNodeName().equals(Const.ATTR_HEIGHT)) {
			String value = node.getNodeValue();
			entity.height = Float.valueOf(value);
			return true;
		}
		return false;
	}

	private boolean parseAttrId(SvgEntity entity, Node node) {
		if (node.getNodeName().equals(Const.ATTR_ID)) {
			String value = node.getNodeValue();
			entity.id = value;
			return true;
		}
		return false;
	}

	private void parseDesc(SvgEntity entity, Node node) {
		if (node.hasChildNodes()) {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node item = list.item(i);
				if (item.getNodeName().equals(Const.TAG_DESC)) {
					HashMap<String, String> map = new HashMap<String, String>();
					String desc = item.getTextContent();
					String lines[] = desc.split("\n");
					for (int j = 0; j < lines.length; j++) {
						String line = lines[j];
						String pairs[] = line.split("=");
						map.put(pairs[0], pairs[1]);
					}
					entity.desc = map;
				}
			}
		}
	}

	public static <T> void printf(T t) {
		System.out.print(t + "\n");
	}
}
