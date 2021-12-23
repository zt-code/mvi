package com.base.lib.base.command;


import androidx.arch.core.util.Function;

/**
 * About : kelin的ResponseCommand
 * 执行的命令事件转换
 */
public class ResponseCommand<T, R> {

    private BindingFunction<R> mExecute;
    private Function<T, R> mFunction;
    private BindingFunction<Boolean> mCanExecute;

    /**
     * like {BindingCommand},but ResponseCommand can return result when command has executed!
     *
     * @param execute function to execute when event occur.
     */
    public ResponseCommand(BindingFunction<R> execute) {
        mExecute = execute;
    }


    public ResponseCommand(Function<T, R> execute) {
        mFunction = execute;
    }


    public ResponseCommand(BindingFunction<R> execute, BindingFunction<Boolean> canExecute) {
        mExecute = execute;
        mCanExecute = canExecute;
    }


    public ResponseCommand(Function<T, R> execute, BindingFunction<Boolean> canExecute) {
        mFunction = execute;
        mCanExecute = canExecute;
    }


    public R execute() {
        if (mExecute != null && canExecute()) {
            return mExecute.call();
        }
        return null;
    }

    private boolean canExecute() {
        if (mCanExecute == null) {
            return true;
        }
        return mCanExecute.call();
    }


    public R execute(T parameter) throws Exception {
        if (mFunction != null && canExecute()) {
            return mFunction.apply(parameter);
        }
        return null;
    }
}
