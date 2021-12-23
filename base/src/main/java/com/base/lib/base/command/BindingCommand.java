package com.base.lib.base.command;


/**
 * About : kelin的ReplyCommand
 * 执行的命令回调, 用于ViewModel与xml之间的数据绑定
 */
public class BindingCommand<T> {

    private BindingAction mExecute;
    private BindingConsumer<T> mConsumer;
    private BindingFunction<Boolean> mCanExecute0;

    public BindingCommand(BindingAction execute) {
        mExecute = execute;
    }

    /**
     * @param execute 带泛型参数的命令绑定
     */
    public BindingCommand(BindingConsumer<T> execute) {
        mConsumer = execute;
    }

    /**
     * @param execute     触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommand(BindingAction execute, BindingFunction<Boolean> canExecute0) {
        mExecute = execute;
        mCanExecute0 = canExecute0;
    }

    /**
     * @param execute     带泛型参数触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommand(BindingConsumer<T> execute, BindingFunction<Boolean> canExecute0) {
        mConsumer = execute;
        mCanExecute0 = canExecute0;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (mExecute != null && canExecute0()) {
            mExecute.call();
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param t 泛型参数
     */
    public void execute(T t) {
        if (mConsumer != null && canExecute0()) {
            mConsumer.call(t);
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private boolean canExecute0() {
        if (mCanExecute0 == null) {
            return true;
        }
        return mCanExecute0.call();
    }
}
