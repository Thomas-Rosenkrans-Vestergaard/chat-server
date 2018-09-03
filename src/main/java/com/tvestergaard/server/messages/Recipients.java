package com.tvestergaard.server.messages;

import com.tvestergaard.server.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Recipients
{

    enum Mode
    {

        /**
         * When a message should be sent to all users.
         */
        TO_ALL,

        /**
         * When a message should be sent to all users, except those returned by {@link Recipients#getList()}.
         */
        TO_ALL_EXCEPT,

        /**
         * When a message should only be sent to the users returned by {@link Recipients#getList()}.
         */
        TO_THESE
    }

    /**
     * Returns the {@link Mode}, signaling the type of recipients.
     *
     * @return The {@link Mode}, signaling the type of recipients.
     */
    Mode getMode();

    /**
     * Returns a list of users. Used in context given by the {@link Recipients#getMode()} method.
     *
     * @return The list of users. Used in context given by the {@link Recipients#getMode()} method. Only returns non-null
     * value when the recipient mode is {@link Mode#TO_THESE}.
     */
    List<User> getList();

    Set<User> getSet();

    public static Recipients toAll()
    {
        return new Recipients()
        {
            @Override public Mode getMode()
            {
                return Mode.TO_ALL;
            }

            @Override public List<User> getList()
            {
                return null;
            }

            @Override public Set<User> getSet()
            {
                return null;
            }
        };
    }

    public static Recipients toAllExcept(User... exceptions)
    {
        Set<User> set = new HashSet<>();
        for (User exception : exceptions)
            set.add(exception);

        return toAllExcept(set);
    }

    public static Recipients toAllExcept(Set<User> exceptions)
    {
        return new Recipients()
        {
            @Override public Mode getMode()
            {
                return Mode.TO_ALL_EXCEPT;
            }

            @Override public List<User> getList()
            {
                return null;
            }

            @Override public Set<User> getSet()
            {
                return exceptions;
            }
        };
    }

    public static Recipients toThese(User... users)
    {
        List<User> list = new ArrayList<>();
        for (User user : users)
            list.add(user);

        return toThese(list);
    }

    public static Recipients toThese(List<User> users)
    {
        return new Recipients()
        {
            @Override public Mode getMode()
            {
                return Mode.TO_THESE;
            }

            @Override public List<User> getList()
            {
                return users;
            }

            @Override public Set<User> getSet()
            {
                return null;
            }
        };
    }
}
