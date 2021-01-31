package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;
import static org.junit.Assert.*;

@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class RecipeTest {

    private static String R_NAME = "TEST";
    private static int R_PRICE = 11;
    private static int R_AMT_COFFEE = 22;
    private static int R_AMT_MILK = 33;
    private static int R_AMT_SUGAR = 44;
    private static int R_AMT_CHOCO = 55;

    private static Recipe newRecipe () {
        return new Recipe(){{
            setName(R_NAME);
            setPrice(R_PRICE);
            setAmtCoffee(R_AMT_COFFEE);
            setAmtMilk(R_AMT_MILK);
            setAmtSugar(R_AMT_SUGAR);
            setAmtChocolate(R_AMT_CHOCO);
        }};
    }

    private static Context newRecipeContext () throws ContextException {
        return new ServiceContext(){{
            putValue("key", R_NAME);
            putValue("price", R_PRICE);
            putValue("amtCoffee", R_AMT_COFFEE);
            putValue("amtMilk", R_AMT_MILK);
            putValue("amtSugar", R_AMT_SUGAR);
            putValue("amtChocolate", R_AMT_CHOCO);
        }};
    }

    private static boolean areRecipesEqual (Recipe r1, Recipe r2) {
        return r1.getName().equals(r2.getName())
            && r1.getPrice() == r2.getPrice()
            && r1.getAmtCoffee() == r2.getAmtCoffee()
            && r1.getAmtChocolate() == r2.getAmtChocolate()
            && r1.getAmtSugar() == r2.getAmtSugar()
            && r1.getAmtMilk() == r2.getAmtMilk();
    }


    @Test
    public void getRecipe () throws ContextException {
        assertEquals(
            newRecipeContext(),
            Recipe.getContext(newRecipe())
        );
    }

    @Test
    public void getContext () throws ContextException {
        assertTrue(
            areRecipesEqual(
                newRecipe(),
                Recipe.getRecipe(newRecipeContext())
            )
        );
    }
}
