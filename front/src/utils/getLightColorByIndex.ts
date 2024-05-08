const availableColors: string[] = [
    '#F6CECE', '#F8ECE0', '#F5F6CE', '#E3F6CE', '#E0F8E0', '#E0F8EC',
    '#E0F8F7', '#CEE3F6', '#E6E0F8', '#F6CEEC', '#F6CEEC', '#81BEF7',
    '#F5ECCE', '#ECF6CE', '#CEF6EC'
];


// export const getLightColorByIndex = (index: number) => availableColors[index % availableColors.length];

const getRandomColor = (colors: string[]): string => colors[Math.floor(Math.random() * colors.length)];

export const getLowestIndexMissingColor = (selectedColors: string[]): string => {
    const missingColor = availableColors.find(color => !selectedColors.includes(color));
    return missingColor || getRandomColor(availableColors);
};

export default getLowestIndexMissingColor;