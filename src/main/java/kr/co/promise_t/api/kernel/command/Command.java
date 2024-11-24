package kr.co.promise_t.api.kernel.command;

public interface Command<T extends CommandModel> {
    void execute(T model);
}
