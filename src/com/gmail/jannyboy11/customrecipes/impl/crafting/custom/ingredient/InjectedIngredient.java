package com.gmail.jannyboy11.customrecipes.impl.crafting.custom.ingredient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.RecipeItemStack;

public class InjectedIngredient implements Predicate<ItemStack> {
	
	private static Class<? extends RecipeItemStack> recipeItemStackInjectedClass;
	public static void inject() {
		RecipeItemStackClassLoader loader = new RecipeItemStackClassLoader();
		try {
			recipeItemStackInjectedClass = (Class<? extends RecipeItemStack>) loader
					.defineClass("net/minecraft/server/v1_12_R1/RecipeItemStackInjected", RecipeItemStackInjectedDump.dump());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final Predicate<? super ItemStack> tester;
	
	public InjectedIngredient(Predicate<? super ItemStack> predicate) {
		this.tester = predicate;
	}

	@Override
	public boolean test(ItemStack itemStack) {
		return tester.test(itemStack);
	}

	
	public RecipeItemStack asNMSIngredient() {
		try {
			Constructor<? extends RecipeItemStack> constructor = recipeItemStackInjectedClass.getConstructor(Predicate.class);
			RecipeItemStack recipeItemStackInjected = constructor.newInstance(this);
			return recipeItemStackInjected;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	
	/*
	 * The class loaded to define the RecipeItemStackInjected class
	 */
	
	private static class RecipeItemStackClassLoader extends ClassLoader {
		public RecipeItemStackClassLoader() {
			super(RecipeItemStack.class.getClassLoader());
		}
		
		public Class<?> defineClass(String name, byte[] bytecodeBytes) {
			return defineClass(name, bytecodeBytes, 0, bytecodeBytes.length);
		}
	}
	
	/*
	 * THANK YOU SO MUCH ASM
	 */

	private static class RecipeItemStackInjectedDump implements Opcodes {

		public static byte[] dump() throws Exception {

			ClassWriter cw = new ClassWriter(0);
			FieldVisitor fv;
			MethodVisitor mv;

			cw.visit(52, ACC_PUBLIC + ACC_SUPER, "net/minecraft/server/v1_12_R1/RecipeItemStackInjected", null,
					"net/minecraft/server/v1_12_R1/RecipeItemStack", null);

			{
				fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "predicate", "Ljava/util/function/Predicate;",
						"Ljava/util/function/Predicate<-Lnet/minecraft/server/v1_12_R1/ItemStack;>;", null);
				fv.visitEnd();
			}
			{
				mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/util/function/Predicate;)V",
						"(Ljava/util/function/Predicate<-Lnet/minecraft/server/v1_12_R1/ItemStack;>;)V", null);
				mv.visitCode();
				Label l0 = new Label();
				mv.visitLabel(l0);
				mv.visitLineNumber(11, l0);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitInsn(ACONST_NULL);
				mv.visitInsn(ACONST_NULL);
				mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_12_R1/RecipeItemStack", "<init>",
						"([Lnet/minecraft/server/v1_12_R1/ItemStack;Ljava/lang/Object;)V", false);
				Label l1 = new Label();
				mv.visitLabel(l1);
				mv.visitLineNumber(12, l1);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESTATIC, "java/util/Objects", "requireNonNull",
						"(Ljava/lang/Object;)Ljava/lang/Object;", false);
				mv.visitTypeInsn(CHECKCAST, "java/util/function/Predicate");
				mv.visitFieldInsn(PUTFIELD, "net/minecraft/server/v1_12_R1/RecipeItemStackInjected", "predicate",
						"Ljava/util/function/Predicate;");
				Label l2 = new Label();
				mv.visitLabel(l2);
				mv.visitLineNumber(13, l2);
				mv.visitInsn(RETURN);
				Label l3 = new Label();
				mv.visitLabel(l3);
				mv.visitLocalVariable("this", "Lnet/minecraft/server/v1_12_R1/RecipeItemStackInjected;", null, l0, l3, 0);
				mv.visitLocalVariable("predicate", "Ljava/util/function/Predicate;",
						"Ljava/util/function/Predicate<-Lnet/minecraft/server/v1_12_R1/ItemStack;>;", l0, l3, 1);
				mv.visitMaxs(3, 2);
				mv.visitEnd();
			}
			{
				mv = cw.visitMethod(ACC_PUBLIC, "apply", "(Lnet/minecraft/server/v1_12_R1/ItemStack;)Z", null, null);
				mv.visitCode();
				Label l0 = new Label();
				mv.visitLabel(l0);
				mv.visitLineNumber(17, l0);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "net/minecraft/server/v1_12_R1/RecipeItemStackInjected", "predicate",
						"Ljava/util/function/Predicate;");
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKEINTERFACE, "java/util/function/Predicate", "test", "(Ljava/lang/Object;)Z",
						true);
				mv.visitInsn(IRETURN);
				Label l1 = new Label();
				mv.visitLabel(l1);
				mv.visitLocalVariable("this", "Lnet/minecraft/server/v1_12_R1/RecipeItemStackInjected;", null, l0, l1, 0);
				mv.visitLocalVariable("itemStack", "Lnet/minecraft/server/v1_12_R1/ItemStack;", null, l0, l1, 1);
				mv.visitMaxs(2, 2);
				mv.visitEnd();
			}
			{
				mv = cw.visitMethod(ACC_PUBLIC, "a", "(Lnet/minecraft/server/v1_12_R1/ItemStack;)Z", null, null);
				mv.visitCode();
				Label l0 = new Label();
				mv.visitLabel(l0);
				mv.visitLineNumber(22, l0);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "net/minecraft/server/v1_12_R1/RecipeItemStackInjected", "predicate",
						"Ljava/util/function/Predicate;");
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKEINTERFACE, "java/util/function/Predicate", "test", "(Ljava/lang/Object;)Z",
						true);
				mv.visitInsn(IRETURN);
				Label l1 = new Label();
				mv.visitLabel(l1);
				mv.visitLocalVariable("this", "Lnet/minecraft/server/v1_12_R1/RecipeItemStackInjected;", null, l0, l1, 0);
				mv.visitLocalVariable("itemStack", "Lnet/minecraft/server/v1_12_R1/ItemStack;", null, l0, l1, 1);
				mv.visitMaxs(2, 2);
				mv.visitEnd();
			}
			cw.visitEnd();

			return cw.toByteArray();
		}
	}

}