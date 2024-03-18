public class LivingThingTest {
    public static void isWhat(LivingThing livingThing) {
      if ( livingThing instanceof Sparrow ) {
        System.out.println("sparrow");
      }
      else if ( livingThing instanceof Bird ) {
        System.out.println("bird");
      }
      else if ( livingThing instanceof LivingThing ) {
        System.out.println("livingthing");
      }
      else {
        System.out.println("unknown");
      }
    }
    
    public static void main(String[] args) {
      Bird bird = new Sparrow();
      isWhat( bird );
    }
    
  }