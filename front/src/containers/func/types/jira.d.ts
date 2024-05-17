
export type AvatarUrls = {
    '16x16': string;
    '24x24': string;
    '32x32': string;
    '48x48': string;
};

export type JiraMembersRes = {
    accountId: string;
    accountType: string;
    active: boolean;
    avatarUrls: AvatarUrls;
    displayName: string;
    self: string;
};


export type JiraInfoRes = {
    "jiraHost": string;
    "jiraProjectKey": string;
    "jiraUsername": string;
    "jiraKey": string;
}