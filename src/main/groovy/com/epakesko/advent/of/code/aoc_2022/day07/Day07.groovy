package com.epakesko.advent.of.code.aoc_2022.day07;

import com.epakesko.advent.of.code.aoc_2022.day06.Day06.Folder
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day07 extends Day{	
	private static final LS = "\$ ls"
	private static final DIR = "dir"
	private static final CD = "\$ cd"
	private static final UP = ".."
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		lines.pop()
		Map<String, Folder> folders = new HashMap<>()
		Folder currentFolder = new Folder("/");
		folders.put("/", currentFolder);
		
		List<Long> sizes = new ArrayList<>()
						
		lines.each { line ->
			if(line.startsWith(DIR)) currentFolder.addFolder(new Folder(line.split(" ")[1], currentFolder))
			else if(line.startsWith(CD)) {
				String destination = line.split(" ")[2]
				if(destination.equals(UP)) currentFolder = goUp(sizes, currentFolder)
				else currentFolder = currentFolder.folders.get(destination)
			}
			else if(!line.equals(LS)) currentFolder.increaseSize(line.split(" ")[0] as Long)
		}
		while(currentFolder) currentFolder = goUp(sizes, currentFolder)
		sizes.findAll { it <= 100000 }.sum()
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		lines.pop()
		Map<String, Folder> folders = new HashMap<>()
		Folder root = new Folder("/")
		Folder currentFolder = new Folder("/");
		folders.put("/", currentFolder);
		
		List<Long> sizes = new ArrayList<>()
		
		lines.each { line ->
			if(line.startsWith(DIR)) currentFolder.addFolder(new Folder(line.split(" ")[1], currentFolder))
			else if(line.startsWith(CD)) {
				String destination = line.split(" ")[2]
				if(destination.equals(UP)) currentFolder = goUp(sizes, currentFolder)
				else currentFolder = currentFolder.folders.get(destination)
			}
			else if(!line.equals(LS)) currentFolder.increaseSize(line.split(" ")[0] as Long)
		}
		while(currentFolder) currentFolder = goUp(sizes, currentFolder)
		
		long requiredSize = folders.get("/").size - 40000000
		sizes.findAll { it >= requiredSize }.min()
	}
	
	private Folder goUp(List sizes, Folder currentFolder) {
		long currentFolderSize = currentFolder.size
		sizes.add(currentFolderSize)
		currentFolder = currentFolder.parent
		if(currentFolder) currentFolder.increaseSize(currentFolderSize)
		return currentFolder
	}
	
	class Folder {
		String name
		Folder parent
		Map<String, Folder> folders
		long size

		public Folder(String name) {
			this.name = name
			folders = new HashMap<>()
		}
		
		public Folder(String name, Folder parent) {
			this(name)
			this.parent = parent
		}
				
		void increaseSize(long size) {
			this.size += size
		}
		
		void addFolder(Folder childFolder) {
			folders.put(childFolder.name, childFolder)
		}
		
		String toString() {
			String result = "($size) folders: $folders"
		}
	}
}
