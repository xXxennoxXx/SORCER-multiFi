package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;

import static org.junit.Assert.*;

@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class InventoryTest {

    private Inventory inventory;
    private Recipe enough, notEnough;

    @Before
    public void setUp () {
        inventory = new Inventory();

        enough = new Recipe(){{
            setName("enough");
            setPrice(50);
            setAmtCoffee(5);
            setAmtMilk(5);
            setAmtSugar(5);
            setAmtChocolate(5);
        }};

        notEnough = new Recipe(){{
            setName("notEnough");
            setPrice(999);
            setAmtCoffee(999);
            setAmtMilk(999);
            setAmtSugar(999);
            setAmtChocolate(999);
        }};
    }

    @Test
    public void enoughIngredients () {
        assertTrue(inventory.enoughIngredients(enough));
        assertFalse(inventory.enoughIngredients(notEnough));
    }
}
