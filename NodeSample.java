import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the nodes of the tree
 * Each node has a name, parent (for non-root) arraylist of type Node
 * Problem - given tree assign height (level) to the node in the tree
 * @author Nitin
 *
 */
public class NodeSample {
	private String name;
	private String parent;
	ArrayList<NodeSample> children;
	//private int height;
	private static HashMap<String,ArrayList<NodeSample>> mainTree 
					= new HashMap<String,ArrayList<NodeSample>>();
	

	public NodeSample(String name, String parent) {
		super();
		this.name = name;
		this.parent = parent;
	}

	public NodeSample(String name, String parent, ArrayList<NodeSample> children) {
		super();
		this.name = name;
		this.parent = parent;
		this.children = children;
	}

	/**
     * This function creates a node
     * @param name
     * @param parent
     * @param children
     * @return node
     */
	private static NodeSample createNode(String name, String parent, String... children) {
		NodeSample node = new NodeSample(name,parent,createChildNodeArray(name,children));
		return node;
	}
	
	/**
     * This function creates arraylist of child nodes
     * @param parent
     * @param children
     * @return ArrayList(childNodeArray)
     */
	private static ArrayList<NodeSample> createChildNodeArray(String parent, String... children) {
		NodeSample childnode = null;
		ArrayList<NodeSample> childNodeArray = new ArrayList<NodeSample>();
		for(String child: children) {
			childnode = new NodeSample(child,parent);
			childNodeArray.add(childnode);
		}
		return childNodeArray;
	}

	/**
	 * Driver function to test the code
	 * @param args
	 */
	public static void main(String[] args) {
		NodeSample node;
		ArrayList<NodeSample> nodeChild;
		
		// Build sample tree - Start
		Map<String,ArrayList<NodeSample>> tree = new HashMap<String,ArrayList<NodeSample>>();
		nodeChild = createChildNodeArray("Kim", "Ron","Mike");
		tree.put("Kim",nodeChild);
		nodeChild = createChildNodeArray("Lean", "Marty");
		tree.put("Lean",nodeChild);
		nodeChild = createChildNodeArray("Ron", "Bob","Lean", "Amanda");
		tree.put("Ron",nodeChild);
		node = createNode("Me","","Joe","Kale");
		tree.put("Me",node.children);
		nodeChild = createChildNodeArray("Kale", "Rick");
		tree.put("Kale",nodeChild);
		nodeChild = createChildNodeArray("Troy", "Rachel","Kim");
		tree.put("Troy",nodeChild);
		nodeChild = createChildNodeArray("Joe", "Mick","Troy", "Jason");
		tree.put("Joe",nodeChild);
		nodeChild = createChildNodeArray("Amanda", "Teri");
		tree.put("Amanda",nodeChild);
		// Build sample tree - End
		
		if(tree.size() != 0)
			assignHeight(tree);
		else
			System.out.println("Please provide tree data");
	}
	
	/**
     * This function assigns height (level) to nodes
     * @param tree
     */
	public static void assignHeight(Map<String,ArrayList<NodeSample>> tree) {
		int level = 1;
		mainTree = new HashMap<String,ArrayList<NodeSample>>(tree);
		String masterNodeName = "";
		masterNodeName = searchMaster(tree);
		
		System.out.println("Master: " + masterNodeName + " at Level:" + level);

		calculateChildHeight(masterNodeName, level);
	}
	
	/**
     * This function calculates height (level) for nodes using recursion
     * @param nodeName
     * @param level
     * @param depth
     */
	public static void calculateChildHeight(String nodeName,int level) {
		String childNodeName = "";
		ArrayList<NodeSample> childnodes = mainTree.get(nodeName);
		
		if(childnodes != null) {
			int sizeChild = childnodes.size();
			for(int childCounter=0;childCounter<sizeChild;childCounter++) {
				NodeSample childnode = (NodeSample)childnodes.get(childCounter);
				
				//display height (level) of the node
				System.out.println("Child: "+childnode.name
									+" at Node Height (Level): "+(level+1)
									+", parent: "+childnode.parent);
				
				childNodeName = childnode.name;
				
				//recursive call
				calculateChildHeight(childNodeName,level+1);
			}
		}
	}
	
	/**
     * This function searches master node and depth of the tree
     * @param tree
     * @return masterName
     */
	public static String searchMaster(Map<String,ArrayList<NodeSample>> tree) {
		ArrayList<String> parentNames = new ArrayList<String>();
		ArrayList<String> childNames = new ArrayList<String>();
		String masterName = "";
		int treeDepth = 0;
		
		Set<Map.Entry<String, ArrayList<NodeSample>>> entries = tree.entrySet();
		for (Map.Entry<String, ArrayList<NodeSample>> entry : entries) {
			String name = entry.getKey();
			parentNames.add(name);
			
			ArrayList<NodeSample> childnodes = (ArrayList<NodeSample>)entry.getValue();
			int sizeChild = childnodes.size();
			for(int j=0;j<sizeChild;j++) {
				NodeSample childnode = (NodeSample)childnodes.get(j);
				childNames.add(childnode.name);
			}
		}
		treeDepth = entries.size();
		for(int childCounter=0;childCounter<treeDepth;childCounter++) {
			if(!childNames.contains(parentNames.get(childCounter))) {
				masterName = parentNames.get(childCounter);
				break;
			}
		}
		return masterName;
	}

}
