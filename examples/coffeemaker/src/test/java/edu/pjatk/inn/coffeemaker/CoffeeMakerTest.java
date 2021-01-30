package edu.pjatk.inn.coffeemaker;

import com.sun.tools.javac.util.List;
import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.ContextException;
import sorcer.service.Routine;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.eo.operator.*;
import static sorcer.so.operator.eval;
import static sorcer.so.operator.exec;

import static org.junit.Assert.*;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class CoffeeMakerTest {
	private final static Logger logger = LoggerFactory.getLogger(CoffeeMakerTest.class);

	private CoffeeMaker coffeeMaker;
	private Inventory inventory;
	private Recipe espresso, mocha, macchiato, americano;

	@Before
	public void setUp() throws ContextException {
		coffeeMaker = new CoffeeMaker();
		inventory = coffeeMaker.checkInventory();

		espresso = new Recipe();
		espresso.setName("espresso");
		espresso.setPrice(50);
		espresso.setAmtCoffee(6);
		espresso.setAmtMilk(1);
		espresso.setAmtSugar(1);
		espresso.setAmtChocolate(0);

		mocha = new Recipe();
		mocha.setName("mocha");
		mocha.setPrice(100);
		mocha.setAmtCoffee(8);
		mocha.setAmtMilk(1);
		mocha.setAmtSugar(1);
		mocha.setAmtChocolate(2);

		macchiato = new Recipe();
		macchiato.setName("macchiato");
		macchiato.setPrice(40);
		macchiato.setAmtCoffee(7);
		macchiato.setAmtMilk(1);
		macchiato.setAmtSugar(2);
		macchiato.setAmtChocolate(0);

		americano = new Recipe();
		americano.setName("americano");
		americano.setPrice(40);
		americano.setAmtCoffee(7);
		americano.setAmtMilk(1);
		americano.setAmtSugar(2);
		americano.setAmtChocolate(0);
	}

	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(espresso));
	}

	@Test
	public void testContextCofee() throws ContextException {
		assertTrue(espresso.getAmtCoffee() == 6);
	}

	@Test
	public void testContextMilk() throws ContextException {
		assertTrue(espresso.getAmtMilk() == 1);
	}

	@Test
	public void addRecepie() throws Exception {
		coffeeMaker.addRecipe(mocha);
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addContextRecepie() throws Exception {
		coffeeMaker.addRecipe(Recipe.getContext(mocha));
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addServiceRecepie() throws Exception {
		Routine cmt = task(sig("addRecipe", coffeeMaker),
						context(types(Recipe.class), args(espresso),
							result("recipe/added")));

		logger.info("isAdded: " + exec(cmt));
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
	}

	@Test
	public void addRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);
	}


    @Test
    public void addRecipe () throws Exception {
        coffeeMaker.addRecipe(espresso);
        assertEquals(
            espresso.getName(),
            coffeeMaker.getRecipeForName(
                espresso.getName()
            ).getName()
        );
    }

	@Test
    public void deleteRecipe () {
	    boolean added = coffeeMaker.addRecipe(espresso);
        assertTrue(added);
	    boolean removed = coffeeMaker.deleteRecipe(espresso);
	    assertTrue(removed);
    }

    @Test
    public void deleteRecipes () {
	    List<Recipe> recipes = List.of(
            espresso,
            macchiato,
            americano
        );

	    recipes.forEach(coffeeMaker::addRecipe);

        assertTrue(coffeeMaker.deleteRecipes());

        recipes.forEach(r -> assertNull(
            coffeeMaker.getRecipeForName(r.getName()))
        );
    }

    @Test
    public void editRecipe () throws Exception {
        coffeeMaker.addRecipe(espresso);
        coffeeMaker.editRecipe(espresso, macchiato);

        Recipe oldRecipe = coffeeMaker.getRecipeForName(espresso.getName());
        Recipe newRecipe = coffeeMaker.getRecipeForName(macchiato.getName());

        assertNull(oldRecipe);
        assertEquals(newRecipe.getName(), macchiato.getName());
    }

    @Test
    public void addInventory () throws Exception {
        coffeeMaker.addInventory(10, 10, 10, 10);

        Inventory inventory = coffeeMaker.checkInventory();

        assertEquals(inventory.getCoffee(), 25);
        assertEquals(inventory.getMilk(), 25);
        assertEquals(inventory.getSugar(), 25);
        assertEquals(inventory.getChocolate(), 25);
    }

    @Test
    public void purchaseCoffee () throws Exception {
        coffeeMaker.addRecipe(espresso);

        int change = coffeeMaker.makeCoffee(espresso,100);

        Inventory inventory = coffeeMaker.checkInventory();

        assertEquals(50, change);
        assertEquals(9, inventory.getCoffee());
        assertEquals(14, inventory.getMilk());
        assertEquals(14, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    public void negativeAmounts () throws Exception {
        Recipe r = espresso;
	    int neg = -1;

        r.setPrice(neg);
        r.setAmtCoffee(neg);
        r.setAmtMilk(neg);
        r.setAmtSugar(neg);
        r.setAmtChocolate(neg);

        assertEquals(r.getPrice(), 0);
        assertEquals(r.getAmtCoffee(), 0);
        assertEquals(r.getAmtMilk(), 0);
        assertEquals(r.getAmtSugar(), 0);
        assertEquals(r.getAmtChocolate(), 0);
    }

	@Test
	public void maxRecipesOverflow () throws Exception {
		boolean add1 = coffeeMaker.addRecipe(espresso);
		boolean add2 = coffeeMaker.addRecipe(macchiato);
		boolean add3 = coffeeMaker.addRecipe(americano);
		boolean add4 = coffeeMaker.addRecipe(mocha);

		assertTrue(add1);
		assertTrue(add2);
		assertTrue(add3);
		assertFalse(add4);
	}
}

