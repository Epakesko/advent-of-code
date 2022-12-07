package com.epakesko.advent.of.code.aoc_2019.Day18

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day18 extends Day {
	private static final String DISTANCE = "distance"
	private static final String DOORS = "doorsInTheWay"
	private static final String KEYS = "keysPickedUpOnTheWay"
	
	@Override
	public Object calculateResult(Object fileName) {
		return -1
		
		List<String> lines = Util.readFile(fileName)
		Map<Integer, List<Integer>> neighbors = new HashMap<>()
		Map<Integer, Map<Integer, Map<String, Integer>>> keyNeighbors = new HashMap<>()
		Map<Integer, Integer> keys = new HashMap<>()
		Map<Integer, Integer> doors = new HashMap<>()
		
		int width = lines[0].size()
		int height = lines.size()
		int robotPosition
		
		for(int i = 1; i < lines.size() - 1; i++) {
			for(int j = 1; j < lines.get(i).size() - 1; j++) {
				String c = lines.get(i).substring(j, j+1)
				if(c.matches(/[#]/)) continue
				int position = i * width + j
				int upPosition = position - width
				int leftPosition = position - 1
				if(c.matches(/[@]/)) robotPosition = position
				if(c.matches(/[a-z]/)) keys.putAt(position, (int)Math.pow(2, c.getBytes()[0] - 97))
				if(c.matches(/[A-Z]/)) doors.putAt(position, (int)Math.pow(2, c.toLowerCase().getBytes()[0] - 97))
				neighbors.put(position, new ArrayList<>())
				if(neighbors.containsKey(leftPosition)) {
					neighbors.get(leftPosition).add(position)
					neighbors.get(position).add(leftPosition)
				}
				if(neighbors.containsKey(upPosition)) {
					neighbors.get(upPosition).add(position)
					neighbors.get(position).add(upPosition)
				}
			}
		}
		
		keys.each { key ->
			keyNeighbors.put(key.key, traverseGraph(neighbors, keys, doors, key.key))
		}
		
		keyNeighbors.put(robotPosition, traverseGraph(neighbors, keys, doors, robotPosition))
		
		runDijkstra(keyNeighbors, keys, robotPosition)
	}
	
	private Map<Integer, Map<String, Integer>> traverseGraph(Map<Integer, List<Integer>> neighbors, Map<Integer, String> keys, Map<Integer, String> doors, int start) {
		Map<Integer, Map<String, Integer>> keyNeighbors =  new HashMap<>()
		Map<Integer, Map<String, Integer>> latestStepWithDoors = new HashMap<>()
		Set<Integer> visitedNodes = new HashSet<>()
		latestStepWithDoors.put(start, new HashMap<>())
		visitedNodes.add(start)
		
		int distance = 0
		while(latestStepWithDoors.size() > 0) {
			distance++
			Map<Integer, Map<String, Integer>> newLatestStepWithDoors = new HashMap<>()
			latestStepWithDoors.each { node, data ->
				int doorsToNewNode = (data[DOORS]?:0) + (doors.get(node)?: 0)
				int keysToNewNode = (data[KEYS]?:0) + (keys.get(node)?: 0)
				newLatestStepWithDoors.putAll(neighbors.get(node).findAll { !visitedNodes.contains(it) }.collectEntries{[it, [(DOORS): doorsToNewNode, (KEYS): keysToNewNode]]})
			}
			newLatestStepWithDoors.findAll { keys.containsKey(it.key) }.each { keyNode ->
				keyNeighbors.put(keyNode.key, [(DISTANCE): distance, (DOORS): keyNode.value[(DOORS)], (KEYS): keyNode.value[(KEYS)]])
			}
			visitedNodes.addAll(newLatestStepWithDoors.keySet())
			latestStepWithDoors = newLatestStepWithDoors
		}
		keyNeighbors
	}
	
	private int runDijkstra(Map<Integer, Map<Integer, Map<String, Integer>>> neighbors, Map<Integer, Integer> keys, List starts) {
		PriorityQueue<Node> queue = new PriorityQueue(26, new NodeComparator())
		Map<String, Integer> distances = new HashMap<>()
		queue.add(new Node(starts, 0, 0))
		distances.put(starts + "-" + 0, 0)
		//println "neighbors: $neighbors"
		int i = 0
		while(i++ < 100) {
			println queue.size()
			Node node = queue.poll()
			if(node.keysCollected == (int)(Math.pow(2, neighbors.size() - starts.size()) - 1)) return node.priority
			node.positions.eachWithIndex { position, idx ->
				//println "neighborPos: " + neighbors.get(position)
				neighbors.get(position).each { neighborNode ->
					List newPositions = []
					newPositions.addAll(node.positions)
					newPositions[idx] = neighborNode.key
					int keysCollected = node.keysCollected + keys.get(neighborNode.key)
					int doorsInTheWay = neighborNode.value[DOORS]
					int keysInTheWay = neighborNode.value[KEYS]
					String destinationNode = newPositions + "-" + keysCollected
					
					//println "    neighborNode:" + neighborNode
					//println "    doors:" + doorsInTheWay
					//println "    keys:" + keysInTheWay
					//println "    collected:" + keysCollected
					
					if(((keysCollected & doorsInTheWay) == doorsInTheWay) && ((keysCollected & keysInTheWay) == keysInTheWay)) {
						int alt = node.priority + neighborNode.value[DISTANCE]
						//println "    alt: " + alt
						//println "    dist: " + distances.get(destinationNode)
						if(!distances.containsKey(destinationNode) || alt < distances.get(destinationNode)) {
							distances.put(destinationNode, alt)
							queue.remove(new Node(newPositions, 0, keysCollected))
							queue.offer(new Node(newPositions, alt, keysCollected))
							//println "        queue:" + queue
						}
					}
				}
			}
		}
		-1
	}
	
	class NodeComparator implements Comparator<Node>{
		public int compare(Node n1, Node n2) {
			Integer.compare(n1.priority, n2.priority)
		}
	}
	
	class Node {
		List positions
		int priority
		int keysCollected
		
		Node(pos, pri, keys) {
			this.positions = pos
			this.priority = pri
			this.keysCollected = keys
		}
		
		@Override
		public boolean equals(Object n) {
			if(n instanceof Node) {
				Node node = n;
				return node.keysCollected == this.keysCollected && node.positions.size() != this.positions.size() && node.positions.every { this.positions.contains(it) }
			}
			false
		}
		
		@Override
		public String toString() {
			"Node{$positions-$keysCollected: $priority}"
		}
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List<String> lines = Util.readFile(fileName)
		Map<Integer, List<Integer>> neighbors = new HashMap<>()
		Map<Integer, Map<Integer, Map<String, Integer>>> keyNeighbors = new HashMap<>()
		Map<Integer, Integer> keys = new HashMap<>()
		Map<Integer, Integer> doors = new HashMap<>()
		
		int width = lines[0].size()
		int height = lines.size()
		List robotPositions = []
		
		for(int i = 1; i < lines.size() - 1; i++) {
			for(int j = 1; j < lines.get(i).size() - 1; j++) {
				String c = lines.get(i).substring(j, j+1)
				if(c.matches(/[#]/)) continue
				int position = i * width + j
				int upPosition = position - width
				int leftPosition = position - 1
				if(c.matches(/[@]/)) robotPositions << position
				if(c.matches(/[a-z]/)) keys.putAt(position, (int)Math.pow(2, c.getBytes()[0] - 97))
				if(c.matches(/[A-Z]/)) doors.putAt(position, (int)Math.pow(2, c.toLowerCase().getBytes()[0] - 97))
				neighbors.put(position, new ArrayList<>())
				if(neighbors.containsKey(leftPosition)) {
					neighbors.get(leftPosition).add(position)
					neighbors.get(position).add(leftPosition)
				}
				if(neighbors.containsKey(upPosition)) {
					neighbors.get(upPosition).add(position)
					neighbors.get(position).add(upPosition)
				}
			}
		}
		
		keys.each { key ->
			keyNeighbors.put(key.key, traverseGraph(neighbors, keys, doors, key.key))
		}
		
		robotPositions.each { robotPosition ->
			keyNeighbors.put(robotPosition, traverseGraph(neighbors, keys, doors, robotPosition))
		}
		
		
		runDijkstra(keyNeighbors, keys, robotPositions)
	}
}
