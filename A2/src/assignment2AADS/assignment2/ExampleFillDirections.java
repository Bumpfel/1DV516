package assignment2AADS.assignment2;

public class ExampleFillDirections {

    private A2Direction[] array;
    private A2Itinerary<A2Direction> itinerary;

    public static void main(String[] args) {

	ExampleFillDirections example = new ExampleFillDirections();
	example.fillSix();
	example.createItinerary();

    }

    private void fillSix() {
	array = new A2Direction[6];

	array[0] = A2Direction.LEFT;
	array[1] = A2Direction.DOWN;
	array[2] = A2Direction.DOWN;
	array[3] = A2Direction.RIGHT;
	array[4] = A2Direction.UP;
	array[5] = A2Direction.LEFT;
    }

    private void createItinerary() {
	itinerary = new MyItinerary(array);

    }

}
