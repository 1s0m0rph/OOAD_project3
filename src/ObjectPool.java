public interface ObjectPool<T>
{
	default T get(String type)
	{
		return get();
	}
	
	default T get()
	{
		return null;
	}
	
	void release(T obj);
	
	void shutdown();
}
