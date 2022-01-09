package github.thyago.spaceflightnewsintegration.notifier;

import github.thyago.spaceflightnewsintegration.domain.model.ErrorIntegrationMessage;

public interface IntegrationErrorNotifier {

    void notify(ErrorIntegrationMessage errorIntegrationMessage);

}
