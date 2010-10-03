package org.tomighty.ioc;

public class Factory {
	
	private Injector injector;
	
	void injector(Injector injector) {
		this.injector = injector;
	}
	
	public <T> T create(Class<T> clazz) {
		return create(clazz, null);
	}
	
	public <T> T create(Class<T> clazz, Object needer) {
		T instance;
		Provider<T> provider = providerFor(clazz);
		if(provider == null) {
			instance = newInstanceOf(clazz);
		} else {
			instance = provider.createFor(needer);
		}
		injector.inject(instance);
		return instance;

	}
	
	@SuppressWarnings("unchecked")
	private <T>	Provider<T> providerFor(Class<T> clazz) {
		String providerClassName = clazz.getName()+"Provider";
		try {
			Class<Provider<T>> providerClass = (Class<Provider<T>>) Class.forName(providerClassName);
			return create(providerClass);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	private static <T> T newInstanceOf(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create an instance of "+clazz, e);
		}
	}

}
