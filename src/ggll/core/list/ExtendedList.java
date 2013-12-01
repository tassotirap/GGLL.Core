package ggll.core.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ExtendedList<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<T> list;
	
	public ExtendedList()
	{
		list = new ArrayList<T>();
	}
	
	public T[] toArray()
	{
		return (T[])list.toArray();
	}
	
	public void addAll(Collection<T> items)
	{
		list.addAll(items);
	}
	
	public void addAll(ExtendedList<T> items)
	{
		for(T t : items.getAll())
		{
			append(t);
		}	
	}
	
	public void addAll(T[] items)
	{
		for(T t : items)
		{
			append(t);
		}		
	}
		
	public ExtendedList(int size)
	{
		list = new ArrayList<T>(size);
	}
	
	public ExtendedList(Collection<T> items)
	{
		list = new ArrayList<T>(items);
	}
	
	public boolean contains(T item)
	{
		return list.contains(item);
	}
	
	public ArrayList<T> getAll()
	{
		return list;
	}

	public int count()
	{
		return list.size();
	}

	public T get(int index)
	{
		if (index < count())
		{
			return list.get(index);
		}
		return null;
	}
	
	public void insertAt(int index, T item)
	{
		index = index < 0 ? 0 : index;
		list.add(index, item);
	}

	public T first()
	{
		if (count() > 0)
		{
			return list.get(0);
		}
		return null;
	}

	public T last()
	{
		if (count() > 0)
		{
			return list.get(count() - 1);
		}
		return null;
	}

	public void append(T item)
	{
		list.add(item);
	}

	public void prepend(T item)
	{
		list.add(0, item);
	}

	public void remove(T item)
	{
		if (list.contains(item))
		{
			list.remove(item);
		}
	}

	public void removeLast()
	{
		if (count() > 0)
		{
			list.remove(count() - 1);
		}
	}

	public void removeFirst()
	{
		if (count() > 0)
		{
			list.remove(0);
		}
	}

	public void removeAll()
	{
		if (count() > 0)
		{
			list.clear();
		}
	}
}
