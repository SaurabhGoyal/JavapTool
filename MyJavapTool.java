import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MyJavapTool {

	Class<?> thisClass, superClass, interfaces[], parameterType[], fieldType,
			returnType;
	Method publicMethod[], privateMethod[];
	Package pkg;
	int modifier;
	Constructor<?> cons[];
	Field publicField[], privateField[];
	String classSource = "";
	PrintWriter pw;

	MyJavapTool(String className, String outputLocation) {

		// checking if it is package
		if (className.endsWith("*")) {
			System.err.println("Packages not supported");
			classSource += ("Packages not supported");
		}

		// getting class object Class type variable thisClass
		else {
			try {
				thisClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				classSource += ("\nInvalid Class name: " + className + " not found\n\n----------------------------------------------------------------------------\n");
				return;
			}

			try {
				pw = new PrintWriter(new File(outputLocation + className + ".doc"));
			} catch (Exception e) {
			}

			// printing package name
			pkg = thisClass.getPackage();
			classSource += ("\nPackage " + pkg.getName() + ";\n\n");

			// printing class modifier
			modifier = thisClass.getModifiers();
			if (Modifier.isPublic(modifier))
				classSource += ("public ");
			if (Modifier.isFinal(modifier))
				classSource += ("final ");
			if (Modifier.isAbstract(modifier))
				classSource += ("Abstract ");

			// printing class name
			classSource += ("class " + className);

			// printing parentclas name
			try {
				superClass = thisClass.getSuperclass();
				if (!superClass.getName().equals(""))
					classSource += (" extends " + superClass.getName());
			} catch (NullPointerException e) {
			}
			// printing all interfaces
			interfaces = thisClass.getInterfaces();
			if (interfaces.length > 0) {
				classSource += (" implements ");
				for (int i = 0; i < interfaces.length; i++) {
					if (i != 0)
						classSource += (" , ");
					classSource += (interfaces[i].getName());
				}
			}

			classSource += ("\n{\n");

			// printing public data members
			classSource += ("//   public data members\n\n");
			publicField = thisClass.getFields();
			for (int i = 0; i < publicField.length; i++) {
				modifier = publicField[i].getModifiers();
				if (Modifier.isPublic(modifier))
					classSource += ("   public ");
				if (Modifier.isFinal(modifier))
					classSource += ("final ");

				fieldType = publicField[i].getType();
				classSource += (fieldType.getName());
				classSource += (" " + publicField[i].getName() + "\n");
			}

			classSource += ("\n");

			// getting private data-members
			classSource += ("//   private data members\n\n");
			privateField = thisClass.getDeclaredFields();
			for (int i = 0; i < privateField.length; i++) {
				privateField[i].setAccessible(true);
				//int length = classSource.length();

				classSource += ("   private ");
				modifier = privateField[i].getModifiers();
				if (Modifier.isTransient(modifier))
					classSource += ("transient ");
				if (Modifier.isStatic(modifier))
					classSource += ("static ");
				if (Modifier.isFinal(modifier))
					classSource += ("final ");
				fieldType = privateField[i].getType();

				if (fieldType.isArray()) {
					classSource += (fieldType.getSimpleName());
					classSource = classSource
							+ (" " + privateField[i].getName() + "\n");
				} else {
					classSource += (fieldType.getName());
					classSource += (" " + privateField[i].getName() + "\n");
				}
			}

			classSource += ("\n");

			// printing constructors
			classSource += ("//   constructors\n\n");
			cons = thisClass.getConstructors();
			for (int i = 0; i < cons.length; i++) {
				modifier = cons[i].getModifiers();
				if (Modifier.isPublic(modifier))
					classSource += ("   public ");
				classSource += (cons[i].getName() + "(");
				parameterType = cons[i].getParameterTypes();
				for (int j = 0; j < parameterType.length; j++) {
					if (j != 0)
						classSource += (",");
					classSource += (parameterType[j].getSimpleName());
				}
				classSource += (")\n");
			}

			classSource += ("\n");

			// printing public Methods
			classSource += ("//   public methods\n\n");
			publicMethod = thisClass.getMethods();
			for (int i = 0; i < publicMethod.length; i++) {
				modifier = publicMethod[i].getModifiers();
				if (Modifier.isPublic(modifier))
					classSource += ("   public ");
				if (Modifier.isStatic(modifier))
					classSource += ("static ");
				if (Modifier.isFinal(modifier))
					classSource += ("final ");
				returnType = publicMethod[i].getReturnType();
				classSource += (returnType.getSimpleName() + " ");
				classSource += (publicMethod[i].getName() + "(");
				parameterType = publicMethod[i].getParameterTypes();
				for (int j = 0; j < parameterType.length; j++) {
					if (j != 0)
						classSource += (",");
					classSource += (parameterType[j].getSimpleName());
				}
				classSource += (")\n");
			}

			classSource += ("\n");

			// closing classSource
			classSource += ("}");
			classSource += ("\n----------------------------------------------------------------------------------\n");
			System.out.println(classSource);
		}
		try {
			pw.print(classSource);
			pw.close();
		} catch (Exception e) {
		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++)
			new MyJavapTool(args[i],"d:\\");

	}

}
