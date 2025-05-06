# Effective Java

# Creating and Destroying Objects

## Item 1 : Consider using public static methods over constructors

### Pros

***Consider using public static methods instead of constructors as methods has meaningful name.
In static method we also reuse or cache immutable class instances.***

```java
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

1. This method returns an instance of Boolean
2. It has a good name
3. It never creates a new instance rather returns same immutable instance each time it is called.

***Static methods can also return any subtype of the return type. EnumSet has no public constructor it only has static
methods. Based on number of elements like elements.size > 64 or elements.size < 64 it returns RegularEnumSet and
JumboEnumSet two hidden subclass of EnumSet.***

```java
enum Type {
    A,
    B,
    C,
    D
}

public static void main(String[] args) {
    EnumSet<Type> set = EnumSet.of(Type.A, Type.B, Type.C, Type.D);
    System.out.println(set.getClass().getName()); // java.util.RegularEnumSet
}
```

### Cons

***Classes without public or protected constructors can not be subclassed. Also finding static factor methods are hard
to find. So, adhere to common naming convention.***

| Method Name                | Purpose                                                                                                                                                                                          |
|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `of(...)`                  | A concise alternative to constructors; commonly used for small value types. <br>**Example:** `Map.of()`, `Set.of()`. Takes multiple parameters. Set<Rank> faceCard = EnumSet.of(JACK,QUEEN,KING) |
| `valueOf(...)`             | More descriptive than `of`; often used for type conversion or parsing. <br>**Example:** `Integer.valueOf("123")`. BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE)                       |
| `getInstance()/instance()` | Returns a **singleton** or shared instance. StackWalker luke = StackWalker.getInstance(options)                                                                                                  |
| `newInstance()/create()`   | Guarantees a **new, distinct instance** on each call. Object newArray = Arrays.newInstance(classObject, arrayLen);                                                                               |
| `from(...)`                | Converts from one type to another. <br>**Example:** `Date.from(Instant)` Date d = Date.from(instance)                                                                                            |
| `type()`                   | Alternative to getType() and newType(). List<Complain> litany = Collections.list(legacyLitany);                                                                                                  |
| `getType()`                | Returns an instance of a **specific subtype**, based on provided arguments. FileStore fs = Files.getFileStore(path).                                                                             |
| `newType()`                | Like `getType()`, but guarantees a **new instance**. BufferedReader r = Files.bewBufferedReader(path)                                                                                            |

---

```java
public class Color {
    private final int red, green, blue;

    private Color(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    // Common factory method names
    public static Color of(int r, int g, int b) {
        return new Color(r, g, b);
    }

    public static Color fromHex(String hex) {
        // Convert hex string to RGB and return a new Color
        return new Color(...);
    }

    public static Color getInstance() {
        return PREDEFINED_INSTANCE;
    }
}
```

## Item 2: Consider a builder when faced constructor with many parameters

### Pros

***Builder is more suitable for classes that have a lot of parameters like 10, 20 or more. Builder scales better.
Telescoping Constructors are harder to read, write and use on client side. It is hard to pass all these parameters and
if someone accidentally switch the position of two parameters of same type, the compiler would not complain but the
program would produce wrong value.***

***Another possible way of solving this issue is, calling the empty constructor then using setter methods. But this
approach has disadvantages so stay with Builder.***

***Builder Pattern is safe. We can validate parameters.***

```java
public class NutritionFacts {
    // Required parameters
    private final int servingSize;
    private final int servings;

    // Optional parameters - initialized to default values
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    // Private constructor - only accessible from the Builder
    private NutritionFacts(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    // Static nested Builder class
    public static class Builder {
        // Required parameters
        private final int servingSize;
        private final int servings;

        // Optional parameters - initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            this.calories = val;
            return this;
        }

        public Builder fat(int val) {
            this.fat = val;
            return this;
        }

        public Builder sodium(int val) {
            this.sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            this.carbohydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    // Getters can be added here if needed
}

public static void main(String[] args) {
    NutritionFacts drinks = new NutritionFacts.Builder(240, 8)
            .calories(100)
            .sodium(35)
            .carbohydrate(27)
            .build();
}
```

### Cors

1. To create an instance you have to create a builder first that might have performance issue
2. More verbose

So, before making a builder make sure it is worthwhile. Use it when parameters goes out of hand (10,20 of them and
grows).

## Item 3

***A singleton is simple a class that is instantiated exactly once. We have 2 ways of making singleton.***

1. public field ⭕

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }
}
```

2. static factory ⭕

```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }
}
```

In both approaches only one instance is created.

3. `A third way to implement a singleton is to declare a single element enum` ✔️

> Preferred

```java
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }
}
```

The provides serialization mechanism for free. Only problem occurs when your singleton must extend a super class other
than an Enum.

## Item 4

Sometimes we write some classes that are grouping of static methods or fields. Like : Math, Arrays. These
classes are not meant to be instantiated. To make these classes completely non-instantiable we have these options:

1. A private constructor

```java
// Not instantiable and extendable
public class Apis {
    private Apis() {
        throw new AssertionError(); // To make sure the constructor is not called inside the class.
    }
}
```

## Item 5: Prefer Dependency Injection to hardwiring resources

`Do not use singleton or static utility class to implement a class that depends on one or more underlying resources and 
do not have the class create these resources directly. Make use of dependency injection. Pass the resources or factories
to create them into the contructor.`

## Item 6: Avoid creating unnecessary objects

***It is often appropriate to a single object instead of creating a new functionally equivalent object each time it is
needed. Reuse can be both faster and more stylish. An object can always be reused if it is immutable.***

```java
String a = new String("Never do this!");
```

# Methods Common to All Object

# Classes and Interfaces

# Generics

# Enums and Annotations

# Lamdas and Streams

# Methods

# General Programming

# Exceptions

# Concurrency

# Serialization