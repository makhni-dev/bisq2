package network.misq.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Executable<T extends ServiceProvider> {
    protected final T serviceProvider;

    public Executable(String[] args) {
        ApplicationOptions applicationOptions = ApplicationOptionsParser.parse(args);
        serviceProvider = createServiceProvider(applicationOptions, args);
        launchApplication(args);
    }

    abstract protected T createServiceProvider(ApplicationOptions applicationOptions, String[] args);

    protected void launchApplication(String[] args) {
        onApplicationLaunched();
    }

    protected void onApplicationLaunched() {
        serviceProvider.readAllPersisted()
                .thenCompose(r -> serviceProvider.initialize())
                .whenComplete((success, throwable) -> {
                    if (success) {
                        onDomainInitialized();
                    } else {
                        onInitializeDomainFailed(throwable);
                    }
                });
    }

    protected void onInitializeDomainFailed(Throwable throwable) {
        throwable.printStackTrace();
    }

    abstract protected void onDomainInitialized();

    public void shutdown() {
        serviceProvider.shutdown();
    }
}