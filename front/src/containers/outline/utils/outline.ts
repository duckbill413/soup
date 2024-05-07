const normalizeUrl = (url?: string) => {
  const urlString = url ?? "";
  if (!/^https?:\/\//i.test(urlString)) {
    return `https://${urlString}`;
  }
  return urlString;
};

export default normalizeUrl