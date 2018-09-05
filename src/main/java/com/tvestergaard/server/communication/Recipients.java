package com.tvestergaard.server.communication;

import com.tvestergaard.server.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Defines a way to define a group of recipients.
 */
public interface Recipients
{

    enum Group
    {

        /**
         * When a message should be sent to all users.
         */
        ALL,

        /**
         * When a message should be sent to all users, except those returned by {@link Recipients#getThese()}.
         */
        ALL_EXCEPT,

        /**
         * When a message should only be sent to the users returned by {@link Recipients#getThese()}.
         */
        TO_THESE
    }

    /**
     * Returns the {@link Group}, signaling the type of recipients.
     *
     * @return The {@link Group}, signaling the type of recipients.
     */
    Group getGroup();

    /**
     * Returns a list of users. Used in context given by the {@link Recipients#getGroup()} method.
     *
     * @return The list of users. Only returns non-null value when the recipient mode is {@link Group#TO_THESE}.
     */
    List<User> getThese();

    /**
     * Returns a set of users. Used in context given by the {@link Recipients#getGroup()} method.
     *
     * @return The set of users. Only returns non-null value when the recipient mode is {@link Group#ALL_EXCEPT}.
     */
    Set<User> getExceptions();

    /**
     * Defines a {@link Recipients} object meant for all connected users.
     *
     * @return The resulting recipients.
     */
    public static Recipients toAll()
    {
        return new Implementation(Group.ALL, null, null);
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
        return new Implementation(Group.ALL_EXCEPT, null, exceptions);
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
        return new Implementation(Group.TO_THESE, users, null);
    }

    class Implementation implements Recipients
    {
        private final Group      group;
        private final List<User> these;
        private final Set<User>  exceptions;

        public Implementation(Group group, List<User> these, Set<User> exceptions)
        {
            this.group = group;
            this.these = these;
            this.exceptions = exceptions;
        }

        /**
         * Returns the {@link Group}, signaling the type of recipients.
         *
         * @return The {@link Group}, signaling the type of recipients.
         */
        @Override public Group getGroup()
        {
            return group;
        }

        /**
         * Returns a list of users. Used in context given by the {@link Recipients#getGroup()} method.
         *
         * @return The list of users. Only returns non-null value when the recipient mode is {@link Group#TO_THESE}.
         */
        @Override public List<User> getThese()
        {
            return these;
        }

        /**
         * Returns a set of users. Used in context given by the {@link Recipients#getGroup()} method.
         *
         * @return The set of users. Only returns non-null value when the recipient mode is {@link Group#ALL_EXCEPT}.
         */
        @Override public Set<User> getExceptions()
        {
            return exceptions;
        }
    }
}
