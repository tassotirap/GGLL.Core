package ggll.core.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ExtendedList<T> implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final ArrayList<T> list;

	public ExtendedList()
	{
		this.list = new ArrayList<T>();
	}

	public ExtendedList(Collection<T> items)
	{
		this.list = new ArrayList<T>(items);
	}

	public ExtendedList(int size)
	{
		this.list = new ArrayList<T>(size);
	}

	public ExtendedList(T[] items)
	{
		this.list = new ArrayList<T>();
		addAll(items);
	}

	public void addAll(Collection<T> items)
	{
		this.list.addAll(items);
	}

	public void addAll(ExtendedList<T> items)
	{
		for (final T t : items.getAll())
		{
			append(t);
		}
	}

	public void addAll(T[] items)
	{
		for (final T t : items)
		{
			append(t);
		}
	}

	public void append(T item)
	{
		this.list.add(item);
	}

	public boolean contains(T item)
	{
		return this.list.contains(item);
	}

	public int count()
	{
		return this.list.size();
	}

	public T first()
	{
		if (count() > 0)
		{
			return this.list.get(0);
		}
		return null;
	}

	public T get(int index)
	{
		if (index < count())
		{
			return this.list.get(index);
		}
		return null;
	}

	public ArrayList<T> getAll()
	{
		return this.list;
	}

	public void insertAt(int index, T item)
	{
		index = index < 0 ? 0 : index;
		this.list.add(index, item);
	}

	public T last()
	{
		if (count() > 0)
		{
			return this.list.get(count() - 1);
		}
		return null;
	}

	public void prepend(T item)
	{
		this.list.add(0, item);
	}

	public void remove(T item)
	{
		if (this.list.contains(item))
		{
			this.list.remove(item);
		}
	}

	public void removeAll()
	{
		if (count() > 0)
		{
			this.list.clear();
		}
	}

	public void removeFirst()
	{
		if (count() > 0)
		{
			this.list.remove(0);
		}
	}

	public void removeLast()
	{
		if (count() > 0)
		{
			this.list.remove(count() - 1);
		}
	}

	public T[] toArray()
	{
		return (T[]) this.list.toArray();
	}
}
