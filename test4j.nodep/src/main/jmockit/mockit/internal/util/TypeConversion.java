/*
 * Copyright (c) 2006-2012 Rogério Liesenfeld
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit.internal.util;

import mockit.external.asm4.*;

import static mockit.external.asm4.Opcodes.*;

public final class TypeConversion
{
   private static final String[] PRIMITIVE_WRAPPER_TYPE = {
      null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer",
      "java/lang/Float", "java/lang/Long", "java/lang/Double"
   };
   private static final String PRIMITIVE_WRAPPER_TYPES =
      "java/lang/Boolean   java/lang/Character java/lang/Byte      java/lang/Short     " +
      "java/lang/Integer   java/lang/Float     java/lang/Long      java/lang/Double";
   private static final String[] UNBOXING_NAME = {
      null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue"
   };
   private static final String[] UNBOXING_DESC = {null, "()Z", "()C", "()B", "()S", "()I", "()F", "()J", "()D"};

   public static void generateCastToObject(MethodVisitor mv, Type type)
   {
      int sort = type.getSort();

      if (sort < Type.ARRAY) {
         String wrapperType = PRIMITIVE_WRAPPER_TYPE[sort];
         String desc = '(' + type.getDescriptor() + ")L" + wrapperType + ';';
         mv.visitMethodInsn(INVOKESTATIC, wrapperType, "valueOf", desc);
      }
   }

   public static void generateCastFromObject(MethodVisitor mv, Type toType)
   {
      int sort = toType.getSort();

      if (sort == Type.VOID) {
         mv.visitInsn(POP);
      }
      else {
         generateTypeCheck(mv, toType);

         if (sort < Type.ARRAY) {
            mv.visitMethodInsn(INVOKEVIRTUAL, PRIMITIVE_WRAPPER_TYPE[sort], UNBOXING_NAME[sort], UNBOXING_DESC[sort]);
         }
      }
   }

   private static void generateTypeCheck(MethodVisitor mv, Type toType)
   {
      int sort = toType.getSort();
      String typeDesc;

      switch (sort) {
         case Type.ARRAY: typeDesc = toType.getDescriptor(); break;
         case Type.OBJECT: typeDesc = toType.getInternalName(); break;
         default: typeDesc = PRIMITIVE_WRAPPER_TYPE[sort];
      }

      mv.visitTypeInsn(CHECKCAST, typeDesc);
   }

   public static void generateUnboxing(MethodVisitor mv, Type parameterType, int opcode)
   {
      if (opcode == ASTORE) {
         generateTypeCheck(mv, parameterType);
         return;
      }

      int sort = parameterType.getSort();
      String typeDesc;

      if (sort < Type.ARRAY) {
         typeDesc = PRIMITIVE_WRAPPER_TYPE[sort];
      }
      else {
         typeDesc = parameterType.getInternalName();
         int i = PRIMITIVE_WRAPPER_TYPES.indexOf(typeDesc);

         if (i >= 0) {
            sort = i / 20 + 1;
         }
         else if (opcode == ISTORE && "java/lang/Number".equals(typeDesc)) {
            sort = Type.INT;
         }
         else {
            sort = Type.INT;
            switch (opcode) {
               case FSTORE: sort = Type.FLOAT; break;
               case LSTORE: sort = Type.LONG; break;
               case DSTORE: sort = Type.DOUBLE;
            }
            typeDesc = PRIMITIVE_WRAPPER_TYPE[sort];
         }
      }

      mv.visitTypeInsn(CHECKCAST, typeDesc);
      mv.visitMethodInsn(INVOKEVIRTUAL, typeDesc, UNBOXING_NAME[sort], UNBOXING_DESC[sort]);
   }

   public static boolean isBoxing(String owner, String name, String desc)
   {
      return desc.charAt(2) == ')' && "valueOf".equals(name) && PRIMITIVE_WRAPPER_TYPES.contains(owner);
   }

   public static boolean isUnboxing(int opcode, String owner, String desc)
   {
      return opcode == INVOKEVIRTUAL && desc.charAt(1) == ')' && PRIMITIVE_WRAPPER_TYPES.contains(owner);
   }
}
