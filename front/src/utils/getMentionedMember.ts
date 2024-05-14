import { Member } from "@/containers/project/types/member";

const regex = /@(\S+)/g;

const getMentionedMember = (message: string, members: Member[]) => {
    const matches = message.match(regex);
    const mentionedIdsSet: Set<string> = new Set();

    if (matches) {
        matches.forEach(match => {
            const nickname = match.substring(1);
            const mentionedMember = members.find(member => member.nickname === nickname);
            if (mentionedMember) {
                mentionedIdsSet.add(mentionedMember.id);
            }
        });
    }

    return Array.from(mentionedIdsSet);
};

export default getMentionedMember;
