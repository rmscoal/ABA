export default async function healthcheckHandler (_req, res, _next) {
  return res.status(200).send("This VM is healthy!");
};
