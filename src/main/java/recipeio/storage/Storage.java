package recipeio.storage;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.util.ArrayList;

import recipeio.Constants;
import recipeio.enums.MealCategory;
import recipeio.recipe.Recipe;
import recipeio.recipe.RecipeList;

public class Storage {
    
    /**
     * Save the list of items to a txt file
     *
     * @throws IOException for any issues saving list to txt file
     */
    public static void saveFile(RecipeList recipeList) throws IOException {
        File myFile = new File(Constants.FILE_PATH);
        FileWriter fw = new FileWriter(myFile, false);
        BufferedWriter bw = new BufferedWriter(fw);

        int recipeSize = recipeList.getSize();
        for (int i = 0; i < recipeSize; i++) {
            Recipe recipe = recipeList.get(i);
            bw.write(recipe.name);
            bw.write(Constants.RECIPE_DELIMETER);
            bw.write(String.valueOf(recipe.cookTime));
            bw.write(Constants.RECIPE_DELIMETER);
            bw.write(String.valueOf(recipe.calories));
            bw.write(Constants.RECIPE_DELIMETER);
            bw.write(String.join(",",recipe.allergies));
            bw.write(Constants.RECIPE_DELIMETER);
            bw.write(recipe.category.toString());
            bw.write(Constants.RECIPE_DELIMETER);
            bw.write(recipe.url);
            bw.newLine();
        }
        bw.close();
    }

    /**
     * Loads the text file into a recipeList
     *
     * @throws FileNotFoundException if file cannot be found
     */
    public static void loadFile(RecipeList recipeList) throws FileNotFoundException {
        File f = new File(Constants.FILE_PATH); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source

        while (s.hasNext()) {
            String line = s.nextLine();
            String name = line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_NAME];
            int cookTime = Integer.parseInt(line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_COOKTIME]);
            int calories = Integer.parseInt(line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_CALORIES]);
            ArrayList<String> allergies = new ArrayList<>();
            allergies.add(line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_ALLERGIES]);
            MealCategory category = MealCategory.valueOf(line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_MEALCATEGORY]);
            String url = line.split(Constants.RECIPE_DELIMETER)[Constants.STORAGE_URL];
            Recipe testRecipe = new Recipe(name, cookTime, calories, allergies, category, url);
            recipeList.add(testRecipe);
        }
    }
}