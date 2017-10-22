package no.roek.nlpged.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Graph implements Serializable {

	protected String id, originalText;
	protected List<Node> nodes;
	protected HashMap<String, List<Edge>> edges;


	public Graph(String id) {
		this();
		this.id = id;
	}
	
	public Graph(String id, String originalText) {
		this();
		this.id = id;
		this.originalText = originalText;
	}

	public Graph() {
		nodes = new ArrayList<>();
		edges = new HashMap<>();
	}

	public void addNode(Node node) {
		if(!edges.containsKey(node.getId())) {
			edges.put(node.getId(), new ArrayList<Edge>());
		}
		nodes.add(node);
	}

	public int getSize() {
		return nodes.size();
	}


	public HashMap<String, List<Edge>> getEdges() {
		return edges;
	}

	public void addEdge(Edge edge) {
		edges.get(edge.getFrom().getId()).add(edge);
	}

	public List<Edge> getEdges(Node node) {
		return getEdges(node.getId());
	}

	public List<Edge> getEdges(String nodeId) {
		return edges.get(nodeId);
	}

	public void removeNode(int i) {
		nodes.remove(i);
	}

	public Node getNode(int i) {
		return nodes.get(i);
	}
	
	public Node getNode(String id) {
		for (Node node : nodes) {
			if(node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}


	public List<Node> getNodes() {
		return nodes;
	}

	public String getId() {
		return id;
	}

	public String getOriginalText() {
		return originalText;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		// Convert to consistently ordered string
		getEdges().entrySet().stream().sorted((Entry<String, List<Edge>> o1, Entry<String, List<Edge>> o2) -> {
			return o1.getKey().compareTo(o2.getKey());	
		}).forEach((Entry<String, List<Edge>> entry) -> {
			b.append(entry.getKey() + "\n");
			entry.getValue().stream().sorted((Edge e1, Edge e2) -> {
				return e1.getId().compareTo(e2.getId());
			}).forEach((Edge e) -> {
				b.append("->" + e.getTo().getLabel() + "\n");
			});
		});
		
		return b.toString();
	}
}
