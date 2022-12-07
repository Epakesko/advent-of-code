package com.epakesko.advent.of.code.aoc_2021.day15;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day15 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<List<Integer>> minPaths = new ArrayList<>()
		
		for(int i = 0; i < lines.size(); i++) {
			minPaths << new ArrayList<>()
			for(int j = 0; j < lines[i].size(); j++) {
				if(i == 0 && j == 0) minPaths[0] << 0
				else if(i == 0) {
					minPaths[i] << (minPaths[i][j-1] + (lines[i][j] as Integer))
				}
				else if(j == 0) {
					minPaths[i] << (minPaths[i-1][j] + (lines[i][j] as Integer))
				}
				else {
					minPaths[i] << (Math.min(minPaths[i][j-1], minPaths[i-1][j]) + (lines[i][j] as Integer))
				}
			}
		}
		
		minPaths.last().last()
	}

	private int dijkstra(List<List<Integer>> graph) {
		Map<Point, Integer> distances = new HashMap<>()
		Map<Point, Point> previous = new HashMap<>()
		Set<Point> visited = new HashSet<>()
		PriorityQueue<Node> queue = new PriorityQueue(graph.size() * graph.size(), new NodeComparator())
		distances.put(new Point(0, 0), 0)
		queue.add(new Node(new Point(0, 0), 0))
		
		while(!queue.empty) {
			Node node = queue.poll()
			Point u = node.point
			if(((u.x as Integer) == (graph.size() - 1)) && ((u.y as Integer) == (graph.size() - 1))) break
			for(Point n : getNeighbors(u, graph.size())) {
				int alt = distances.get(u) + graph[n.x as Integer][n.y as Integer]
				if(!distances.containsKey(n) || alt < distances.get(n)) {
					distances.put(n, alt)
					queue.remove(new Node(n))
					queue.offer(new Node(n, alt))
				}
			}
		}
		distances.get(new Point(graph.size() - 1, graph.size() - 1))
	}
	
	class NodeComparator implements Comparator<Node>{
		public int compare(Node n1, Node n2) {
			Integer.compare(n1.priority, n2.priority)
		}
	}
	
	class Node {
		Point point
		int priority
		
		Node(p) {
			this.point = p
		}
		
		Node(p, pri) {
			this.point = p
			this.priority = pri
		}
		
		@Override
		public boolean equals(Object n) {
			if(n instanceof Node) {
				Node node = n;
				return node.point.x == this.point.x && node.point.y == this.point.y
			}
			false
		}
	}
	
	private List<Point> getNeighbors(Point p, int graphSize) {
		List<Point> neighbors = new ArrayList<>()
		int x = p.x as Integer
		int y = p.y as Integer
		if(x != 0) neighbors.add(new Point(x-1, y))
		if(y != 0) neighbors.add(new Point(x, y-1))
		if(x != graphSize - 1) neighbors.add(new Point(x+1, y))
		if(y != graphSize - 1) neighbors.add(new Point(x, y+1))
		return neighbors
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		List<List<Integer>> fullMap = new ArrayList<>()
		
		for(int i = 0; i < lines.size() * 5; i++) {
			fullMap << new ArrayList<>()
			int iMod = i % lines.size()
			int iExtra = i.intdiv(lines.size())
			for(int j = 0; j < lines[iMod].size() * 5; j++) {
				int jMod = j % lines[iMod].size()
				int jExtra = j.intdiv(lines[iMod].size())
				
				int num = (((lines[iMod][jMod] as Integer) + iExtra + jExtra) % 9)
				if(num == 0) num = 9
				
				if(i == 0 && j == 0) fullMap[0] << 0
				else fullMap[i] << num
			}
		}
		
		dijkstra(fullMap)
	}
}
