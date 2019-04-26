package test.myutils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class CollectionUtil {
	
	/**
	 * 求交集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> List<T> getIntersection(List<T> list1,List<T> list2) {
		List<T> o1 = list1.size() >= list2.size() ? list2 : list1;
		List<T> o2 = list1.size() >= list2.size() ? list1 : list2;
		List<T> result = new ArrayList<>(o1);
		result.retainAll(o2);
		return result;
	}
	
	public static <T> List<T> convertSetToSortList(Set<T> set,Comparator<T> comparator){
		@SuppressWarnings("unchecked")
		T[] array = (T[]) set.toArray();
		List<T> result = new ArrayList<T>();
		Collections.addAll(result,array);
		Collections.sort(result,comparator);
		return result;
	}
	
	public static <T> List<T> convertSetToSortList(Set<T> set){
		Comparator<T> comparator = new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.toString().compareTo(o2.toString());
			}
		};
		return convertSetToSortList(set,comparator);
	}
	
	public static <T> List<T> convertSetToList(Set<T> set){
		@SuppressWarnings("unchecked")
		T[] array = (T[]) set.toArray();
		List<T> result = new ArrayList<T>();
		Collections.addAll(result,array);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] addAll(T[] array1, T[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        T[] joinedArray = (T[]) Array.newInstance(array1.getClass().getComponentType(),
                                                            array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            final Class<?> type1 = array1.getClass().getComponentType();
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "+type1.getName());
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }
	
	@SuppressWarnings("unchecked")
	public static <T> T[] clone(Object[] array) {
        if (array == null) {
            return null;
        }
        return (T[]) array.clone();
    }
}
