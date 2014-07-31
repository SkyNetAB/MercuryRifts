package mercuryrifts.portal;

import mercuryrifts.mercuryrifts;

public class PortalException extends Exception
{
    private static final long serialVersionUID = 7990987289131589119L;

    public PortalException(String message)
    {
        super(mercuryrifts.localizeError(message));
    }

    public PortalException(String message, boolean localize)
    {
        super(localize ? mercuryrifts.localizeError(message) : message);
    }
}
