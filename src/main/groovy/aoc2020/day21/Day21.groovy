package aoc2020.day21

import aoc2020.Day
import aoc2020.common.Util

class Day21 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map allergensInIngredients = [:]
		Map pairedAllergensIngredients = [:]
		List ingredientList = []
		lines.each { String line ->
			List splittedLine = line.split(" \\(contains ")
			
			Set ingredients = splittedLine[0].split(" ")
			ingredientList.addAll(ingredients)
			List allergens = splittedLine[1].substring(0, splittedLine[1].size()-1).split(", ")
			
			allergens.each { String allergen ->
				allergensInIngredients[allergen] = allergensInIngredients[allergen]? allergensInIngredients[allergen].intersect(ingredients) : ingredients
			}
		}
		
		while(allergensInIngredients.size()) {
			Map.Entry pairedAllergenIngredient = allergensInIngredients.find { allergenInIngredients ->
				allergenInIngredients.value.size() == 1
			}
			
			allergensInIngredients.remove(pairedAllergenIngredient.key)
			allergensInIngredients.keySet().each{
			    allergensInIngredients[it]-=pairedAllergenIngredient.value
			}
			pairedAllergensIngredients[pairedAllergenIngredient.key] = pairedAllergenIngredient.value
		}
		
		pairedAllergensIngredients.values().each {
			ingredientList -= it
		}
		
		ingredientList.size()
	}

	@Override
	public Object calculateResult2(Object fileName) {
				List lines = Util.readFile(fileName)
		
		Map allergensInIngredients = [:]
		Map pairedAllergensIngredients = [:]
		List ingredientList = []
		lines.each { String line ->
			List splittedLine = line.split(" \\(contains ")
			
			Set ingredients = splittedLine[0].split(" ")
			ingredientList.addAll(ingredients)
			List allergens = splittedLine[1].substring(0, splittedLine[1].size()-1).split(", ")
			
			allergens.each { String allergen ->
				allergensInIngredients[allergen] = allergensInIngredients[allergen]? allergensInIngredients[allergen].intersect(ingredients) : ingredients
			}
		}
				
		while(allergensInIngredients.size()) {
			Map.Entry pairedAllergenIngredient = allergensInIngredients.find { allergenInIngredients ->
				allergenInIngredients.value.size() == 1
			}
			
			allergensInIngredients.remove(pairedAllergenIngredient.key)
			allergensInIngredients.keySet().each{
			    allergensInIngredients[it]-=pairedAllergenIngredient.value
			}
			pairedAllergensIngredients[pairedAllergenIngredient.key] = pairedAllergenIngredient.value
		}
		
		pairedAllergensIngredients.values().each {
			ingredientList -= it
		}
		
		pairedAllergensIngredients.sort().values().join(",").replaceAll("\\[", "").replaceAll("\\]", "")
	}
}
